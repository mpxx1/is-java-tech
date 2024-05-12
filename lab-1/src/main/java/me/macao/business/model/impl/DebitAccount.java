package me.macao.business.model.impl;

import lombok.Getter;
import me.macao.business.exception.TransactionException;
import me.macao.business.model.interfaces.BankAccount;

/**
 * A class representing a debit account.
 *
 * <p>A debit account is a type of bank account that allows the account holder to deposit and withdraw money.
 * Debit accounts typically have a lower interest rate than other types of bank accounts, such as savings accounts or credit accounts.
 * This is because the bank is taking on less risk by holding the account holder's money.
 *
 * <p>Debit accounts can be a useful financial tool for managing your everyday expenses.
 * They can also be used to save money for short-term goals, such as a vacation or a new car.
 */
@Getter
public class DebitAccount
        implements BankAccount {

  /**
   * The unique ID of this debit account.
   */
  private final long id;

  /**
   * The current balance of this debit account.
   */
  private double amount;

  /**
   * The current amount of interest that has been earned on this debit account.
   */
  private double percentAmount = 0;

  /**
   * The annual percentage yield (APY) of this debit account.
   */
  private final double percent;

  /**
   * Constructs a new debit account with the given ID, initial balance, and APY.
   *
   * @param id the ID of the new debit account.
   * @param amount the initial balance of the new debit account.
   * @param percent the APY of the new debit account.
   */
  public DebitAccount(long id, double amount, double percent) {
    this.id = id;
    this.amount = amount;
    this.percent = percent;
  }

  /**
   * Calculates the daily percentage amount for this debit account.
   *
   * @return the daily percentage amount for this debit account.
   */
  private double dailyPercent() {
    return percent / 365.0;
  }

  @Override
  public double getPercent() throws NoSuchFieldException {
    return percent;
  }

  @Override
  public void commissionUpd(double commission) {
  }

  @Override
  public void dailyPercentAmountUpd() {
    percentAmount += amount * dailyPercent();
  }

  @Override
  public void monthlyPercentAmountUpd() {
    amount += percentAmount;

    percentAmount = 0.0;
  }

  @Override
  public double hypotheticalPercentAmount(int days) {
    return amount * (1.0 + dailyPercent() * days);
  }

  @Override
  public void withdraw(double money) throws TransactionException {

    if (money > amount)
      throw new TransactionException("Can not withdraw more than you have!");

    amount -= money;
  }

  @Override
  public void refill(double money)
          throws TransactionException {

    amount += money;
  }
}