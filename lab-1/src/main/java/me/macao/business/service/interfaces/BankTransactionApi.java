package me.macao.business.service.interfaces;

import lombok.NonNull;
import me.macao.business.exception.NoSuchAccountException;
import me.macao.business.exception.TransactionException;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.Transaction;

/**
 * An interface representing a bank transaction API.
 */
public interface BankTransactionApi {

  /**
   * Returns the ID of the bank.
   *
   * @return the ID of the bank.
   */
  short bankId();

  /**
   * Returns the bank account with the given ID.
   *
   * @param account the ID of the bank account to return.
   * @return the bank account with the given ID.
   * @throws NoSuchAccountException if the bank account does not exist.
   */
  @NonNull
  BankAccount getAccount(long account) throws NoSuchAccountException;

  /**
   * Executes the given transaction.
   *
   * @param transaction the transaction to execute.
   * @throws TransactionException if the transaction could not be executed.
   */
  void exec(Transaction transaction) throws TransactionException;

  /**
   * Aborts the given transaction.
   *
   * @param transaction the transaction to abort.
   * @throws TransactionException if the transaction could not be aborted.
   */
  void abort(Transaction transaction) throws TransactionException;
}
