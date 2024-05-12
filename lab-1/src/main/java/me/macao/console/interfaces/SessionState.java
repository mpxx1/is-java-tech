package me.macao.console.interfaces;

import lombok.NonNull;
import me.macao.business.exception.*;
import me.macao.business.model.enums.BankAccountType;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.DepositRange;
import me.macao.business.model.interfaces.Message;
import me.macao.business.service.interfaces.Bank;

import java.util.Collection;

/**
 * An interface representing a session state.
 */
public interface SessionState {

  /**
   * Logs in a user to the session state.
   *
   * @param bankName the name of the bank the user is logging in to.
   * @param userId the ID of the user logging in.
   * @param passwd the password of the user logging in.
   * @return the new session state.
   * @throws LoginException if the user could not be logged in.
   * @throws NotPermitedException if the user is not permitted to log in.
   */
  @NonNull
  SessionState login(@NonNull String bankName, long userId, short passwd)
          throws LoginException, NotPermitedException;

  /**
   * Logs in an admin to the session state.
   *
   * @param id the ID of the admin logging in.
   * @param passwd the password of the admin logging in.
   * @return the new session state.
   * @throws LoginException if the admin could not be logged in.
   * @throws NotPermitedException if the admin is not permitted to log in.
   */
  @NonNull
  SessionState adminLogin(short id, short passwd) throws LoginException, NotPermitedException;

  /**
   * Logs out the current user from the session state.
   *
   * @return the new session state.
   */
  @NonNull
  SessionState logout();

  /**
   * Returns the bank accounts of the current user.
   *
   * @return the bank accounts of the current user.
   * @throws NotPermitedException if the user is not permitted to get their bank accounts.
   */
  @NonNull
  Collection<@NonNull BankAccount> getAccounts() throws NotPermitedException;

  /**
   * Creates a new bank account for the current user.
   *
   * @param accountType the type of bank account to create.
   * @param days the number of days the bank account will be active for.
   * @param amount the amount of money to deposit into the bank account.
   * @return the newly created bank account.
   * @throws NotPermitedException if the user is not permitted to create a bank account.
   * @throws AccountCreateException if the bank account could not be created.
   */
  @NonNull
  BankAccount createAccount(
          BankAccountType accountType, int days, double amount)
          throws NotPermitedException, AccountCreateException;

  /**
   * Withdraws money from the specified bank account.
   *
   * @param accountId the ID of the bank account to withdraw money from.
   * @param amount the amount of money to withdraw.
   * @throws TransactionException if the transaction could not be completed.
   * @throws NotPermitedException if the user is not permitted to withdraw money from the bank
   *     account.
   * @throws WrongAccountException if the bank account does not belong to the current user.
   */
  void withdraw(long accountId, double amount)
          throws TransactionException, NotPermitedException, WrongAccountException;

  /**
   * Deposits money into the specified bank account.
   *
   * @param accountId the ID of the bank account to deposit money into.
   * @param amount the amount of money to deposit.
   * @throws TransactionException if the transaction could not be completed.
   * @throws NotPermitedException if the user is not permitted to deposit money into the bank
   *     account.
   * @throws WrongAccountException if the bank account does not belong to the current user.
   */
  void refill(long accountId, double amount)
          throws TransactionException, NotPermitedException, WrongAccountException;

  /**
   * Transfers money from one bank account to another.
   *
   * @param accountFrom the ID of the bank account to transfer money from.
   * @param accountTo the ID of the bank account to transfer money to.
   * @param amount the amount of money to transfer.
   * @throws TransactionException if the transaction could not be completed.
   * @throws NotPermitedException if the user is not permitted to transfer money from the bank
   *     account.
   * @throws WrongAccountException if either bank account does not belong to the current user.
   */
  void transfer(long accountFrom, long accountTo, double amount)
          throws TransactionException, NotPermitedException, WrongAccountException;

  /**
   * Returns the info messages for the current user.
   *
   * @return the info messages for the current user.
   * @throws NotPermitedException if the user is not permitted to get the info messages.
   */
  @NonNull
  Collection<Message> getInfo() throws NotPermitedException;

  /**
   * Clears the info messages for the current user.
   *
   * @throws NotPermitedException if the user is not permitted to clear the info messages.
   */
  void clearInfo() throws NotPermitedException;

  /**
   * Adds a new bank to the central bank.
   *
   * @param name the name of the bank to add.
   * @param transferLimit the transfer limit for the bank.
   * @param withdrawLimit the withdraw limit for the bank.
   * @param debitPercent the debit percent for the bank.
   * @param depositRange the deposit range for the bank.
   * @param creditLimit the credit limit for the bank.
   * @param commission the credit commission for the bank.
   * @return the newly added bank.
   * @throws BankCreateException if the bank could not be created.
   * @throws NotPermitedException if the user is not permitted to add a new bank.
   */
  Bank addBank(
          @NonNull String name,
          double transferLimit,
          double withdrawLimit,
          double debitPercent,
          @NonNull DepositRange depositRange,
          double creditLimit,
          double commission)
          throws BankCreateException, NotPermitedException;

  /**
   * Notifies all banks of a change in the central bank's policies.
   *
   * @throws NotPermitedException if the user is not permitted to notify the banks.
   */
  void notifyBanks() throws NotPermitedException;
}