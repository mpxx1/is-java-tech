package me.macao.business.service.interfaces;

import lombok.NonNull;
import me.macao.business.exception.*;
import me.macao.business.model.enums.BankAccountType;
import me.macao.business.model.interfaces.*;

import java.util.Collection;

/**
 * An interface representing a bank.
 */
public interface Bank {

  /**
   * Returns the name of the bank.
   *
   * @return the name of the bank.
   */
  @NonNull
  String getName();

  /**
   * Returns the ID of the bank.
   *
   * @return the ID of the bank.
   */
  short getId();

  /**
   * Sets the transfer limit for the bank.
   *
   * @param transferLimit the transfer limit to set.
   */
  void setTransferLimit(double transferLimit);

  /**
   * Returns the transfer limit for the bank.
   *
   * @return the transfer limit for the bank.
   */
  double getTransferLimit();

  /**
   * Sets the withdraw limit for the bank.
   *
   * @param withdrawLimit the withdraw limit to set.
   */
  void setWithdrawLimit(double withdrawLimit);

  /**
   * Returns the withdraw limit for the bank.
   *
   * @return the withdraw limit for the bank.
   */
  double getWithdrawLimit();

  /**
   * Returns the debit percent for the bank.
   *
   * @return the debit percent for the bank.
   */
  double getDebitPercent();

  /**
   * Sets the debit percent for the bank.
   *
   * @param percent the debit percent to set.
   */
  void setDebitPercent(double percent);

  /**
   * Returns the deposit range for the bank.
   *
   * @return the deposit range for the bank.
   */
  DepositRange getDepositRange();

  /**
   * Sets the deposit range for the bank.
   *
   * @param depositRange the deposit range to set.
   */
  void setDepositRange(DepositRange depositRange);

  /**
   * Returns the credit limit for the bank.
   *
   * @return the credit limit for the bank.
   */
  double getCreditLimit();

  /**
   * Sets the credit limit for the bank.
   *
   * @param creditLimit the credit limit to set.
   */
  void setCreditLimit(double creditLimit);

  /**
   * Returns the credit commission for the bank.
   *
   * @return the credit commission for the bank.
   */
  double getCreditCommission();

  /**
   * Sets the credit commission for the bank.
   *
   * @param commission the credit commission to set.
   */
  void setCreditCommission(double commission);

  /**
   * Updates the payment for the bank.
   */
  void updatePayment();

  /**
   * Updates the monthly payment for the bank.
   */
  void monthlyPaymentUpd();

  /**
   * Updates the commission for the bank.
   */
  void updateCommission();

  /**
   * Updates the bank's information.
   *
   * @param message the message to update the bank's information with.
   */
  void updateInfo(Message message);

  /**
   * Creates a new user.
   *
   * @param name the name of the user.
   * @param surname the surname of the user.
   * @param address the address of the user.
   * @param passport the passport number of the user.
   * @return the newly created user.
   */
  @NonNull
  User createUser(
          @NonNull String name,
          @NonNull String surname,
          Address address,
          Long passport);

  /**
   * Creates a new bank account.
   *
   * @param userId the ID of the user to create the bank account for.
   * @param accountType the type of bank account to create.
   * @param days the number of days the bank account will be active for.
   * @param amount the amount of money to deposit into the bank account.
   * @return the newly created bank account.
   * @throws AccountCreateException if the bank account could not be created.
   */
  @NonNull
  BankAccount createAccount(
          long userId,
          @NonNull BankAccountType accountType,
          int days,
          double amount)
          throws AccountCreateException;

  /**
   * Returns the bank accounts of the user.
   *
   * @param userId the ID of the user whose bank accounts to return.
   * @return the bank accounts of the user.
   * @throws NoSuchUserException if the user does not exist.
   */
  @NonNull
  Collection<@NonNull BankAccount> getUserBankAccounts(long userId)
          throws NoSuchUserException;

  /**
   * Returns the bank account with the given ID.
   *
   * @param accountId the ID of the bank account to return.
   * @return the bank account with the given ID.
   * @throws NoSuchAccountException if the bank account does not exist.
   */
  @NonNull
  BankAccount getAccount(long accountId)
          throws NoSuchAccountException;

  /**
   * Returns the user with the given ID and password.
   *
   * @param userId the ID of the user to return.
   * @param passwd the password of the user to return.
   * @return the user with the given ID and password.
   * @throws NoSuchUserException if the user does not exist.
   */
  User getUser(long userId, short passwd)
          throws NoSuchUserException;

  /**
   * Returns the bank of the account with the given ID.
   *
   * @param accountId the ID of the account whose bank to return.
   * @return the bank of the account with the given ID.
   * @throws NoSuchBankException if the bank does not exist.
   */
  BankTransactionApi getAnotherBank(long accountId)
          throws NoSuchBankException;

  /**
   * Executes the given transaction.
   *
   * @param transaction the transaction to execute.
   * @throws TransactionException if the transaction could not be executed.
   */
  void execTransaction(@NonNull Transaction transaction)
          throws TransactionException;

  /**
   * Aborts the given transaction.
   *
   * @param transaction the transaction to abort.
   * @throws TransactionException if the transaction could not be aborted.
   */
  void abortTransaction(@NonNull Transaction transaction)
          throws TransactionException;
}
