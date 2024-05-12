package me.macao.business.service.interfaces;

import lombok.NonNull;
import me.macao.business.model.interfaces.DepositRange;

/**
 * An interface representing the bank constants API.
 */
public interface BankConstsApi {

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
   * Returns the transfer limit for the bank.
   *
   * @return the transfer limit for the bank.
   */
  double getTransferLimit();

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
   * Returns the deposit range for the bank.
   *
   * @return the deposit range for the bank.
   */
  @NonNull
  DepositRange getDepositRange();

  /**
   * Returns the credit limit for the bank.
   *
   * @return the credit limit for the bank.
   */
  double getCreditLimit();

  /**
   * Returns the credit commission for the bank.
   *
   * @return the credit commission for the bank.
   */
  double getCreditCommission();
}