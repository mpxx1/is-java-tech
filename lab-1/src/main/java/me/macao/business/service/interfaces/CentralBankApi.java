package me.macao.business.service.interfaces;

import lombok.NonNull;
import me.macao.business.exception.NoSuchBankException;

/**
 * An interface representing the central bank API.
 */
public interface CentralBankApi {

  /**
   * Returns the bank transaction API for the bank with the given ID.
   *
   * @param id the ID of the bank to return the bank transaction API for.
   * @return the bank transaction API for the bank with the given ID.
   * @throws NoSuchBankException if the bank with the given ID does not exist.
   */
  @NonNull
  BankTransactionApi getBankById(short id) throws NoSuchBankException;
}