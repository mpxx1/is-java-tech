package me.macao.business.model.impl;

import lombok.NonNull;
import me.macao.business.exception.TransactionException;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.Transaction;

/**
 * A class representing a refill transaction.
 */
public class RefillTransaction implements Transaction {

  private final BankAccount account;
  private final double amount;

  /**
   * Constructs a new refill transaction.
   *
   * @param account the account to refill.
   * @param amount the amount to refill the account with.
   */
  public RefillTransaction(@NonNull BankAccount account, double amount) {
    this.account = account;
    this.amount = amount;
  }

  @Override
  public void exec() throws TransactionException {

    if (amount < 0)
      throw new TransactionException("Negative amount!");

    account.refill(amount);
  }

  @Override
  public void abort() throws TransactionException {
    account.withdraw(amount);
  }

  @Override
  public @NonNull String info() {
    return String.format(
            """
                   operation:REFILL
                   account:%d
                   amount:%f
                """,
            account.getId(),
            amount
    );
  }
}