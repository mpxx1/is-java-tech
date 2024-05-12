package me.macao.business.model.impl;

import lombok.NonNull;
import me.macao.business.exception.TransactionException;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.Transaction;

import java.util.Objects;

/**
 * A class representing a transfer instant 1 transaction.
 */
public class TransferInst1Transaction implements Transaction {

  private final long accountFrom;
  @NonNull
  private final BankAccount accountTo;
  private final double amount;

  /**
   * Constructs a new transfer instant 1 transaction.
   *
   * @param accountFrom the account to transfer money from.
   * @param accountTo the account to transfer money to.
   * @param amount the amount of money to transfer.
   */
  public TransferInst1Transaction(long accountFrom, @NonNull BankAccount accountTo, double amount) {
    this.accountFrom = accountFrom;
    this.accountTo = accountTo;
    this.amount = amount;
  }

  @Override
  public void exec() throws TransactionException {
    accountTo.refill(amount);
  }

  @Override
  public void abort() throws TransactionException {
    accountTo.withdraw(amount);
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
            accountFrom,
            accountTo.getId(),
            amount);
  }
}