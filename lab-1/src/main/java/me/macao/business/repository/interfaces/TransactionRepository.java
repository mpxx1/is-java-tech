package me.macao.business.repository.interfaces;

import me.macao.business.exception.NoSuchTransactionException;
import me.macao.business.model.interfaces.Transaction;

/**
 * An interface representing a repository of transactions.
 */
public interface TransactionRepository {

  /**
   * Adds a transaction to the repository.
   *
   * @param transaction the transaction to add.
   */
  void add(Transaction transaction);

  /**
   * Removes the given transaction from the repository.
   *
   * @param transaction the transaction to remove.
   * @throws NoSuchTransactionException if the given transaction does not exist.
   */
  void remove(Transaction transaction) throws NoSuchTransactionException;
}
