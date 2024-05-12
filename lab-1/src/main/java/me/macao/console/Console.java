package me.macao.console;

import lombok.NonNull;
import me.macao.business.exception.*;
import me.macao.business.model.enums.BankAccountType;
import me.macao.business.model.impl.DepositRangeImpl;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.DepositRange;
import me.macao.business.model.interfaces.User;
import me.macao.business.service.impl.CentralBankImpl;
import me.macao.business.service.interfaces.Bank;
import me.macao.console.impl.SessionApiImpl;
import me.macao.console.interfaces.SessionApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A class representing a console.
 */
public class Console {

  private static void helpUser() {
    System.out.println("""
            Commands:
            
            login <bankName> <userId> <passwd>
            
            logout
            
            refill <amount>
            
            withdraw <amount>
            
            transfer <accountTo> <amount>
            
            createAcc [credit / debit / deposit] {days} {amount}
                                                    if deposit
                                                    
            createUser ...
                                                    
            showMes
            
            clrMes
            """);
  }

  private static void helpAdmin() {
    System.out.println("""
            Commands:
            
            addBank <name> <transferLimit> <withdrawLimit> <debitPcnt> <creditLimit> <commission> --dr <depositRange>
            
              depositRange:
                min:max:pct,min:max:pct...
            
            notify
            """);
  }

  private static BankAccountType getBankAccountType(String type) {
    if (type.equals("deposit")) return BankAccountType.DEPOSIT;
    if (type.equals("debit")) return BankAccountType.DEBIT;
    return BankAccountType.CREDIT;
  }

  private static @NonNull DepositRange drParse(String input)
          throws ParseException {

    return new DepositRangeImpl(new ArrayList<>());  //todo
  }

  public static void main(String[] args) {

    SessionApi session = new SessionApiImpl();
    BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in)
    );

    helpUser();

    main: while (true) {
      String inp = null;

      try {

        inp = reader.readLine();
      } catch (IOException e) {

        System.err.println("Input Exception, sth went wrong");
      }

      String[] input = inp.split(" ");

      try {

        switch (input[0]) {
          case "notify" -> session.notifyBanks();

          case "addBank" -> {
            if (input.length != 9) {
              System.out.println("Wrong addBank usage");
              helpAdmin();
              continue;
            }

            double[] doubleArgs = new double[5];
            for (int i = 2; i < 7; ++i) {
              try {

                var elem = Double.parseDouble(input[i]);
                doubleArgs[i - 2] = elem;
              } catch (NumberFormatException e) {

                System.out.println("Wrong addBank usage; Number parse error");
                helpAdmin();
                continue main;
              }
            }

            if (!input[7].equals("--dr")) {
              helpAdmin();
              continue;
            }

            try {
              Bank b = session.addBank(
                      input[1],
                      doubleArgs[0],
                      doubleArgs[1],
                      doubleArgs[2],
                      drParse(input[8]),
                      doubleArgs[3],
                      doubleArgs[4]
              );
            } catch (ParseException e) {
              System.out.println("Can not parse deposit percent");
              helpAdmin();
            } catch (BankCreateException e) {
              System.out.println("Can not create bank\n" + e.getMessage());
              helpAdmin();
            }
          }

          case "logout" -> session.logout();

          case "login" -> {

            try {

              session.login(input[1], Long.parseLong(input[2]), Short.parseShort(input[3]));
            } catch (Exception e) {

              System.err.println(e.getMessage());
              helpUser();
            }
          }

          case "loginAdmin" -> {
            try {

              session.adminLogin(Short.parseShort(input[1]), Short.parseShort(input[2]));
            } catch (Exception e) {

              System.err.println(e.getMessage());
              helpAdmin();
            }
          }

          case "getAccs" -> {
            try {

              Collection<BankAccount> accs = session.getAccounts();

              for (var elem : accs) {
                System.out.println(elem.getId());
                System.out.println(elem.getAmount());
              }
            } catch (NotPermitedException e) {

              System.err.println("Login first");
              helpUser();
            }
          }

          case "createAcc" -> {

            BankAccount account;

            try {

               account = session.createAccount(
                      getBankAccountType(input[1]), Integer.parseInt(input[2]), Double.parseDouble(input[3])
              );
            } catch (AccountCreateException e) {

              System.err.println("Can not create account" + e.getMessage());
              helpUser();
              continue main;
            }

            System.out.println(account.getId());
            System.out.println(account.getAmount());
          }

          case "createUser" -> {

            Bank bank;

            try {

              bank = CentralBankImpl
                      .getInstance()
                      .getBankByName(input[1]);
            } catch (NoSuchBankException e) {

              System.err.println("Can not create user in this bank, try another");
              continue main;
            }

            User newUser = null;

            if (input.length == 4) {

               newUser = bank.createUser(
                      input[2],
                      input[3],
                      null,
                      null
              );

            } else if (input.length == 6) {

              newUser = bank.createUser(
                      input[2],
                      input[3],
                      null, // todo parseAddress(input[4])
                      Long.parseLong(input[5])
              );

            } else {
              helpUser();
              continue main;
            }

            System.out.println(
                    "login:" +
                            newUser.getId() +
                            '\n' +
                            "passwd:" +
                            newUser.getPasswd()
            );
          }

          case "withdraw" -> {
            try {

              session.withdraw(Long.parseLong(input[1]), Double.parseDouble(input[2]));
            } catch (Exception e) {
              System.err.println(e.getMessage());
              helpUser();
            }
          }

          case "refill" -> {
            try {

              session.refill(Long.parseLong(input[1]), Double.parseDouble(input[2]));
            } catch (Exception e) {
              System.err.println(e.getMessage());
              helpUser();
            }
          }

          case "transfer" -> {
            try {

              session.transfer(Long.parseLong(input[1]), Long.parseLong(input[2]), Double.parseDouble(input[3]));
            } catch (Exception e) {
              System.err.println(e.getMessage());
              helpUser();
            }
          }

          case "getInfo" -> session.getInfo();

          case "clearInfo" -> session.clearInfo();

          default -> helpUser();
        }
      } catch (NotPermitedException e) {

        System.err.println("Not permited operation");
        helpUser();
      }
    }
  }
}
