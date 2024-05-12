package me.macao.business.service.impl;

import lombok.NonNull;
import me.macao.business.exception.NoSuchAccountException;
import me.macao.business.exception.TransactionException;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.Transaction;
import me.macao.business.service.interfaces.Bank;
import me.macao.business.service.interfaces.BankTransactionApi;

/**
 * A class representing a bank transaction API implementation.
 */
public class BankTransactionApiImpl
  implements BankTransactionApi {
  private final Bank bank;

  public BankTransactionApiImpl(Bank bank) {
    this.bank = bank;
  }

  @Override
  public short bankId() {
    return bank.getId();
  }

  @Override
  public @NonNull BankAccount getAccount(long account)
          throws NoSuchAccountException {

    return bank.getAccount(account);
  }

  @Override
  public void exec(Transaction transaction)
          throws TransactionException {

    bank.execTransaction(transaction);
  }

  @Override
  public void abort(Transaction transaction)
          throws TransactionException {

    bank.abortTransaction(transaction);
  }
}
