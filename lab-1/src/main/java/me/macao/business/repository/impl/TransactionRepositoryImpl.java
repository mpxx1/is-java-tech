package me.macao.business.repository.impl;

import lombok.NonNull;
import me.macao.business.model.interfaces.Transaction;
import me.macao.business.repository.interfaces.TransactionRepository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class representing a repository of transactions.
 */
public class TransactionRepositoryImpl
  implements TransactionRepository {

  @NonNull
  private final Collection<@NonNull Transaction> transactions = new ArrayList<>();

  @Override
  public void add(Transaction transaction) {
    transactions.add(transaction);
  }

  @Override
  public void remove(Transaction transaction) {
    transactions.remove(transaction);
  }
}
