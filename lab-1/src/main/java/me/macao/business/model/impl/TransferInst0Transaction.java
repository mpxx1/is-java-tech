package me.macao.business.model.impl;

import lombok.NonNull;
import me.macao.business.exception.NoSuchAccountException;
import me.macao.business.exception.NoSuchUserException;
import me.macao.business.exception.TransactionException;
import me.macao.business.model.enums.UserAccountType;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.Transaction;
import me.macao.business.service.interfaces.BankConstsApi;
import me.macao.business.service.interfaces.BankTransactionApi;

/**
 * A class representing a transfer instant 0 transaction.
 */
public class TransferInst0Transaction implements Transaction {

  private final @NonNull BankAccount accountFrom;
  private final long accountTo;
  private final double amount;
  private final @NonNull BankTransactionApi anotherBank;
  private final BankConstsApi bankConsts;
  private final UserAccountType userAccountType;

  /**
   * Constructs a new transfer instant 0 transaction.
   *
   * @param accountFrom the account to transfer money from.
   * @param accountTo the account to transfer money to.
   * @param amount the amount of money to transfer.
   * @param anotherBank the bank to transfer the money to.
   * @param bankConsts the bank constants API.
   * @param userAccountType the user account type.
   */
  public TransferInst0Transaction(
          @NonNull BankAccount accountFrom,
          long accountTo,
          double amount,
          @NonNull BankTransactionApi anotherBank,
          BankConstsApi bankConsts,
          UserAccountType userAccountType) {
    this.accountFrom = accountFrom;
    this.accountTo = accountTo;
    this.amount = amount;
    this.anotherBank = anotherBank;
    this.bankConsts = bankConsts;
    this.userAccountType = userAccountType;
  }

  @Override
  public void exec() throws TransactionException {

    if (userAccountType == UserAccountType.DOUBTFUL &&
            amount > bankConsts.getTransferLimit()) {
      throw new TransactionException("DOUBTFUL User account, set passport to unlock transfer limit");
    }

    if (amount < 0)
      throw new TransactionException("Negative amount!");


    accountFrom.withdraw(amount);

    try {
      BankAccount anotherAccount = anotherBank.getAccount(accountTo);

      anotherBank
              .exec(
                      new TransferInst1Transaction(
                              accountFrom.getId(),
                              anotherAccount,
                              amount));
    } catch (Exception e) {
      accountFrom.refill(amount);

      throw new TransactionException("another bank didn't sign transfer");
    }
  }

  @Override
  public void abort() throws TransactionException {
    accountFrom.refill(amount);

    try {
      BankAccount anotherAccount = anotherBank.getAccount(accountTo);

      anotherBank
              .abort(
                      new TransferInst1Transaction(
                              accountFrom.getId(),
                              anotherAccount,
                              amount));
    } catch (Exception ignored) {
    }
  }

  @Override
  public @NonNull String info() {
    return String.format(
            """
                   operation:TRANSFER         
                   accountFrom:%d
                   accountTo:%d
                   amount:%f
                """,
            accountFrom.getId(),
            accountTo,
            amount);
  }
}
