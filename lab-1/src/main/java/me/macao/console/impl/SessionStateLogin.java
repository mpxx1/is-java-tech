package me.macao.console.impl;

import lombok.NonNull;
import me.macao.business.exception.*;
import me.macao.business.model.enums.BankAccountType;
import me.macao.business.model.impl.RefillTransaction;
import me.macao.business.model.impl.TransferInst0Transaction;
import me.macao.business.model.impl.WithdrawTransaction;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.DepositRange;
import me.macao.business.service.impl.BankConstsApiImpl;
import me.macao.business.service.interfaces.BankConstsApi;
import me.macao.console.interfaces.SessionState;
import me.macao.business.model.interfaces.Message;
import me.macao.business.model.interfaces.User;
import me.macao.business.service.interfaces.Bank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class representing a session user login state implementation.
 */
public class SessionStateLogin
  implements SessionState {

  @NonNull
  private final Bank bank;
  @NonNull
  private final BankConstsApi bankConsts;
  @NonNull
  private final User user;

  public SessionStateLogin(@NonNull Bank bank, @NonNull User user) {
    this.bank = bank;
    this.user = user;
    this.bankConsts = new BankConstsApiImpl(bank);
  }

  @Override
  public @NonNull SessionState login(@NonNull String bankName, long userId, short passwd)
          throws NotPermitedException {

    throw new NotPermitedException();
  }

  @Override
  public @NonNull SessionState adminLogin(short id, short passwd) throws LoginException, NotPermitedException {
    throw new NotPermitedException();
  }

  @Override
  public @NonNull SessionState logout() {

    return new SessionStateLogout();
  }

  @Override
  public @NonNull Collection<@NonNull BankAccount> getAccounts() {
    try {
      return bank
              .getUserBankAccounts(user.getId());
    } catch (Exception ignored) {
    }

    return new ArrayList<>();
  }

  @Override
  public @NonNull BankAccount
  createAccount(BankAccountType accountType, int days, double amount)
          throws AccountCreateException {

    return bank.createAccount(
            user.getId(),
            accountType,
            days,
            amount
    );
  }

  @Override
  public void withdraw(long accountId, double amount)
          throws TransactionException, WrongAccountException {

    if (
            getAccounts()
                    .stream()
                    .filter(acc -> acc.getId() == accountId)
                    .toList()
                    .isEmpty()
    ) throw new WrongAccountException("You don't own this account");

    try {

      bank
              .execTransaction(
                      new WithdrawTransaction(
                              bank.getAccount(accountId),
                              amount,
                              bankConsts,
                              user.getUserAccountType()
                      )
              );
    } catch (NoSuchAccountException e) {

      throw new TransactionException();
    }
  }

  @Override
  public void refill(long accountId, double amount)
          throws TransactionException, WrongAccountException {

    if (
            getAccounts()
                    .stream()
                    .filter(acc -> acc.getId() == accountId)
                    .toList()
                    .isEmpty()
    ) throw new WrongAccountException("You don't own this account");

    try {

      bank
              .execTransaction(
                      new RefillTransaction(
                              bank.getAccount(accountId),
                              amount
                      )
              );
    } catch (NoSuchAccountException e) {

      throw new TransactionException();
    }
  }

  @Override
  public void transfer(long accountFrom, long accountTo, double amount)
          throws TransactionException, WrongAccountException {

    if (
            getAccounts()
                    .stream()
                    .filter(acc -> acc.getId() == accountFrom)
                    .toList()
                    .isEmpty()
    ) throw new WrongAccountException("You don't own this account");

    try {

      bank
              .execTransaction(
                      new TransferInst0Transaction(
                              bank.getAccount(accountFrom),
                              accountTo,
                              amount,
                              bank.getAnotherBank(accountTo),
                              bankConsts,
                              user.getUserAccountType()
                      )
              );
    } catch (Exception e) {

      throw new TransactionException();
    }
  }

  @Override
  public @NonNull Collection<Message> getInfo() {
    return user.getMessages();
  }

  @Override
  public void clearInfo() {

    user.clearMessages();
  }

  @Override
  public Bank addBank(@NonNull String name, double transferLimit, double withdrawLimit, double debitPercent, @NonNull DepositRange depositRange, double creditLimit, double commission) throws BankCreateException, NotPermitedException {
    throw new NotPermitedException();
  }

  @Override
  public void notifyBanks() throws NotPermitedException {
    throw new NotPermitedException();
  }
}
