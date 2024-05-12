package me.macao.business.service.impl;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import me.macao.business.exception.*;
import me.macao.business.model.enums.BankAccountType;
import me.macao.business.model.impl.*;
import me.macao.business.model.interfaces.*;
import me.macao.business.repository.impl.AccountRepositoryImpl;
import me.macao.business.repository.impl.TransactionRepositoryImpl;
import me.macao.business.repository.impl.UserRepositoryImpl;
import me.macao.business.repository.interfaces.AccountRepository;
import me.macao.business.repository.interfaces.TransactionRepository;
import me.macao.business.repository.interfaces.UserRepository;
import me.macao.business.service.interfaces.Bank;
import me.macao.business.service.interfaces.BankTransactionApi;
import me.macao.business.service.interfaces.CentralBankApi;

import java.util.Collection;
import java.util.Random;

/**
 * A class representing a bank implementation.
 */
@Data
@Builder
public class BankImpl
  implements Bank {

  private final @NonNull String name;
  private final short id;
  private double transferLimit;
  private double withdrawLimit;
  private double debitPercent;
  private @NonNull DepositRange depositRange;
  private double creditLimit;
  private double creditCommission;

  private final Random rand = new Random();
  private final @NonNull CentralBankApi cb;

  private final @NonNull AccountRepository accountsRepo = new AccountRepositoryImpl();
  private final @NonNull UserRepository usersRepo = new UserRepositoryImpl();
  private final @NonNull TransactionRepository transactionsRepo = new TransactionRepositoryImpl();

  @Override
  public void updatePayment() {
    accountsRepo
            .all()
            .forEach(
                    BankAccount::dailyPercentAmountUpd
            );
  }

  @Override
  public void monthlyPaymentUpd() {
    accountsRepo
            .all()
            .forEach(
                    BankAccount::monthlyPercentAmountUpd
            );
  }

  @Override
  public void updateCommission() {
    accountsRepo
            .all()
            .forEach(
                    acc -> acc.commissionUpd(creditCommission)
            );
  }

  @Override
  public void updateInfo(Message message) {
    usersRepo
            .all()
            .forEach(
                    usr -> usr.sendMessage(message)
            );
  }

  @Override
  public @NonNull User
  createUser(
          @NonNull String name,
          @NonNull String surname,
          Address address,
          Long passport
  ) {

    var usr = new UserImpl(
            name,
            surname,
            address,
            passport
    );

    usersRepo.add(usr);

    return usr;
  }

  @Override
  public @NonNull BankAccount
  createAccount(
          long userId,
          @NonNull BankAccountType accountType,
          int days,
          double amount
  ) throws AccountCreateException {

    var id = rand.nextLong();
    var pct = getDepositPct(amount);

    var ba = switch (accountType) {

      case DEBIT -> new DebitAccount(
              id,
              amount,
              getDebitPercent()
      );

      case DEPOSIT -> new DepositAccount(
              id,
              pct,
              days,
              amount
      );

      case CREDIT -> new CreditAccount(
              id
      );
    };

    accountsRepo.add(userId, ba);

    return ba;
  }

  @Override
  public @NonNull Collection<@NonNull BankAccount>
  getUserBankAccounts(long userId)
          throws NoSuchUserException {

    return accountsRepo.get(userId);
  }

  @Override
  public @NonNull BankAccount getAccount(long accountId)
          throws NoSuchAccountException {

    return accountsRepo.getAccount(accountId);
  }

  @Override
  public @NonNull User getUser(long userId, short passwd)
          throws NoSuchUserException {

    var usr = usersRepo.get(userId);

    if (usr.getPasswd() != passwd)
      throw new NoSuchUserException("Wrong id or password");

    return usr;
  }

  @Override
  public @NonNull BankTransactionApi
  getAnotherBank(long accountId)
          throws NoSuchBankException {

    String bankId = String.valueOf(accountId);
    bankId = bankId.substring(0, 4);

    return cb.getBankById(
            Short.parseShort(bankId)
    );
  }

  @Override
  public void execTransaction(@NonNull Transaction transaction)
          throws TransactionException {

    transaction.exec();
    transactionsRepo.add(transaction);
  }

  @Override
  public void abortTransaction(@NonNull Transaction transaction)
          throws TransactionException {

    try {
      transactionsRepo.remove(transaction);
    } catch (NoSuchTransactionException e) {
      return;
    }

    transaction.abort();
  }

  private double getDepositPct(double amount) {
    var rngs = getDepositRange().ranges();

    for (var elem : rngs) {
      if (amount < elem.end())
        return elem.percent();
    }

    return 1.0;
  }
}
