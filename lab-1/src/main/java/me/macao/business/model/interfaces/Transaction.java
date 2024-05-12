package me.macao.business.model.interfaces;

import lombok.NonNull;
import me.macao.business.exception.TransactionException;

/**
 * An interface representing a transaction.
 */
public interface Transaction {

  /**
   * Executes the transaction.
   *
   * @throws TransactionException if the transaction cannot be executed.
   */
  void exec() throws TransactionException;

  /**
   * Aborts the transaction.
   *
   * @throws TransactionException if the transaction cannot be aborted.
   */
  void abort() throws TransactionException;

  /**
   * Returns a string representation of the transaction.
   *
   * @return a string representation of the transaction.
   */
  @NonNull String info();
}
