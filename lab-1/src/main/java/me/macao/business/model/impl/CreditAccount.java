package me.macao.business.model.impl;

import lombok.Getter;
import me.macao.business.exception.TransactionException;
import me.macao.business.model.interfaces.BankAccount;

/**
 * A class representing a credit account.
 *
 * <p>A credit account is a type of bank account that allows the account holder to borrow money from the bank.
 * The borrowed money can be used for any purpose, such as paying for expenses, making investments, or consolidating debt.
 * Credit accounts typically have a higher interest rate than other types of bank accounts, such as savings accounts or checking accounts.
 * This is because the bank is taking on more risk by lending money to the account holder.
 *
 * <p>Credit accounts can be a useful financial tool, but it is important to use them responsibly.
 * If you borrow more money than you can afford to repay, you may end up in debt.
 * It is also important to make sure that you understand the terms and conditions of your credit account before you open it.
 * This will help you avoid any surprises down the road.
 */
@Getter
public class CreditAccount implements BankAccount {

  /**
   * The unique ID of this credit account.
   */
  private final long id;

  /**
   * The current balance of this credit account.
   */
  private double amount = 0.0;

  /**
   * Constructs a new credit account with the given ID.
   *
   * @param id the ID of the new credit account.
   */
  public CreditAccount(long id) {
    this.id = id;
  }

  @Override
  public double getPercent() throws NoSuchFieldException {
    throw new NoSuchFieldException();
  }

  @Override
  public void commissionUpd(double commission) {
    amount -= commission;
  }

  @Override
  public void dailyPercentAmountUpd() {
  }

  @Override
  public void monthlyPercentAmountUpd() {
  }

  @Override
  public double hypotheticalPercentAmount(int days) throws NoSuchFieldException {
    throw new NoSuchFieldException();
  }

  @Override
  public void withdraw(double money)
          throws TransactionException {

    amount -= money;
  }

  @Override
  public void refill(double money)
          throws TransactionException {

    amount += money;
  }
}