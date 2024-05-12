package me.macao.business.model.impl;

import lombok.Getter;
import me.macao.business.exception.AccountCreateException;
import me.macao.business.exception.TransactionException;
import me.macao.business.model.interfaces.BankAccount;


/**
 * A class representing a deposit account.
 *
 * <p>A deposit account is a type of bank account that allows the account holder to deposit money and earn interest on it.
 * Deposit accounts typically have a higher interest rate than other types of bank accounts, such as savings accounts or checking accounts.
 * This is because the bank is taking on more risk by lending the account holder's money to other borrowers.
 *
 * <p>Deposit accounts can be a useful financial tool for saving money for long-term goals, such as retirement or a child's education.
 * They can also be used to generate income from interest payments.
 */
@Getter
public class DepositAccount implements BankAccount {

  /**
   * The unique ID of this deposit account.
   */
  private final long id;

  /**
   * The annual percentage yield (APY) of this deposit account.
   */
  private final double percent;

  /**
   * The number of days remaining until the deposit account matures.
   */
  private int days;

  /**
   * The current balance of this deposit account.
   */
  private double amount;

  /**
   * Constructs a new deposit account with the given ID, APY, number of days until maturity, and initial balance.
   *
   * @param id the ID of the new deposit account.
   * @param percent the APY of the new deposit account.
   * @param days the number of days until the new deposit account matures.
   * @param amount the initial balance of the new deposit account.
   * @throws AccountCreateException if the number of days until maturity is not positive.
   */
  public DepositAccount(long id, double percent, int days, double amount) throws AccountCreateException {
    if (days <= 0) {
      throw new AccountCreateException();
    }

    this.id = id;
    this.percent = percent;
    this.days = days;
    this.amount = amount;
  }

  @Override
  public double getPercent() {
    return percent;
  }

  @Override
  public void commissionUpd(double commission) {
  }

  @Override
  public void dailyPercentAmountUpd() {
    if (days == 0) {
      return;
    }

    --days;
    amount *= (1.0 + percent / 365.0);
  }

  @Override
  public void monthlyPercentAmountUpd() {
  }

  @Override
  public double hypotheticalPercentAmount(int days) {
    double hypothetical = amount;
    for ( int i = 0; i < days; ++i )
      hypothetical *= (1.0 + percent / 365.0);

    return hypothetical;
  }

  @Override
  public void withdraw(double money)
          throws TransactionException {

    if (days > 0) {
      throw new TransactionException("Can not withdraw until finishing period");
    }

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
