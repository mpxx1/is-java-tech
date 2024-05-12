package me.macao.business.service.interfaces;

import lombok.NonNull;
import me.macao.business.exception.BankCreateException;
import me.macao.business.exception.NoSuchBankException;
import me.macao.business.model.interfaces.DepositRange;

/**
 * An interface representing a central bank.
 */
public interface CentralBank {

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
   */
  @NonNull
  Bank addBank(
          @NonNull String name,
          double transferLimit,
          double withdrawLimit,
          double debitPercent,
          @NonNull DepositRange depositRange,
          double creditLimit,
          double commission)
          throws BankCreateException;

  /**
   * Notifies all banks of a change in the central bank's policies.
   */
  void notifyBanks();

  /**
   * Returns the bank transaction API for the bank with the given ID.
   *
   * @param id the ID of the bank to return the bank transaction API for.
   * @return the bank transaction API for the bank with the given ID.
   * @throws NoSuchBankException if the bank with the given ID does not exist.
   */
  @NonNull
  BankTransactionApi getBankById(short id) throws NoSuchBankException;

  /**
   * Returns the bank with the given name.
   *
   * @param bank the name of the bank to return.
   * @return the bank with the given name.
   * @throws NoSuchBankException if the bank with the given name does not exist.
   */
  @NonNull
  Bank getBankByName(@NonNull String bank) throws NoSuchBankException;
}