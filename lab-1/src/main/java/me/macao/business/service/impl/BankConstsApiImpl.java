package me.macao.business.service.impl;

import lombok.NonNull;
import me.macao.business.model.interfaces.DepositRange;
import me.macao.business.service.interfaces.Bank;
import me.macao.business.service.interfaces.BankConstsApi;

/**
 * A class representing the bank constants API implementation.
 */
public class BankConstsApiImpl
  implements BankConstsApi {

  private final Bank bank;

  public BankConstsApiImpl(Bank bank) { this.bank = bank; }

  @Override
  public @NonNull String getName() {
    return bank.getName();
  }

  @Override
  public short getId() {
    return bank.getId();
  }

  @Override
  public double getTransferLimit() {
    return bank.getTransferLimit();
  }

  @Override
  public double getWithdrawLimit() {
    return bank.getWithdrawLimit();
  }

  @Override
  public double getDebitPercent() {
    return bank.getDebitPercent();
  }

  @Override
  @NonNull
  public DepositRange getDepositRange() {
    return bank.getDepositRange();
  }

  @Override
  public double getCreditLimit() {
    return bank.getCreditLimit();
  }

  @Override
  public double getCreditCommission() {
    return bank.getCreditCommission();
  }
}
