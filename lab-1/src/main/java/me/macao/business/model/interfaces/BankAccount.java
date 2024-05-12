package me.macao.business.model.interfaces;

import me.macao.business.exception.TransactionException;

/**
 * An interface representing a bank account.
 */
public interface BankAccount {

  /**
   * Returns the unique ID of this bank account.
   *
   * @return the unique ID of this bank account.
   */
  long getId();

  /**
   * Returns the annual percentage yield (APY) of this bank account.
   *
   * @return the annual percentage yield (APY) of this bank account.
   * @throws NoSuchFieldException if the APY field is not found.
   */
  double getPercent() throws NoSuchFieldException;

  /**
   * Returns the current balance of this bank account.
   *
   * @return the current balance of this bank account.
   */
  double getAmount();

  /**
   * Updates the commission for this bank account.
   *
   * @param commission the new commission for this bank account.
   */
  void commissionUpd(double commission);

  /**
   * Updates the daily percent amount for this bank account.
   */
  void dailyPercentAmountUpd();

  /**
   * Updates the monthly percent amount for this bank account.
   */
  void monthlyPercentAmountUpd();

  /**
   * Calculates the hypothetical percent amount for this bank account for the given number of days.
   *
   * @param days the number of days to calculate the hypothetical percent amount for.
   * @return the hypothetical percent amount for this bank account for the given number of days.
   * @throws NoSuchFieldException if the APY field is not found.
   */
  double hypotheticalPercentAmount(int days) throws NoSuchFieldException;

  /**
   * Withdraws the given amount of money from this bank account.
   *
   * @param money the amount of money to withdraw.
   * @throws TransactionException if the withdrawal is not successful.
   */
  void withdraw(double money) throws TransactionException;

  /**
   * Deposits the given amount of money into this bank account.
   *
   * @param money the amount of money to deposit.
   * @throws TransactionException if the deposit is not successful.
   */
  void refill(double money) throws TransactionException;
}
