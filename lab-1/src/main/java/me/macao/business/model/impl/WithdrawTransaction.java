package me.macao.business.model.impl;

import lombok.NonNull;
import me.macao.business.exception.TransactionException;
import me.macao.business.model.enums.UserAccountType;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.Transaction;
import me.macao.business.service.interfaces.BankConstsApi;

/**
 * A class representing a withdraw transaction.
 */
public class WithdrawTransaction implements Transaction {

  private final BankAccount account;
  private final double amount;
  private final BankConstsApi bankConsts;
  private final UserAccountType userAccountType;

  /**
   * Constructs a new withdraw transaction.
   *
   * @param account the account to withdraw money from.
   * @param amount the amount of money to withdraw.
   * @param bankConsts the bank constants API.
   * @param userAccountType the user account type.
   */
  public WithdrawTransaction(
          @NonNull BankAccount account,
          double amount,
          BankConstsApi bankConsts,
          UserAccountType userAccountType) {
    this.account = account;
    this.amount = amount;
    this.bankConsts = bankConsts;
    this.userAccountType = userAccountType;
  }

  @Override
  public void exec() throws TransactionException {

    if (amount < 0)
      throw new TransactionException("Negative amount!");

    if (userAccountType == UserAccountType.DOUBTFUL &&
            amount > bankConsts.getWithdrawLimit())
      throw new TransactionException("DOUBTFUL User account, set passport to unlock withdraw limit");

    if (account.getAmount() < 0 &&
            bankConsts.getCreditLimit() - Math.abs(account.getAmount()) < amount)
      throw new TransactionException("Can not withdraw more than credit limit!");

    account.withdraw(amount);
  }

  @Override
  public void abort() throws TransactionException {
    account.refill(amount);
  }

  @Override
  public @NonNull String info() {
    return String.format(
            """
                   operation:WITHDRAW
                   account:%d
                   amount:%f
            """,
            account.getId(),
            amount);
  }
}
