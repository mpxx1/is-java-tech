package me.macao.business.repository.interfaces;

import lombok.NonNull;
import me.macao.business.exception.NoSuchBankException;
import me.macao.business.service.interfaces.Bank;

import java.util.Collection;

/**
 * An interface representing a repository of banks.
 */
public interface BankRepository {

  /**
   * Returns all banks in the repository.
   *
   * @return a collection of all banks in the repository.
   */
  @NonNull
  Collection<@NonNull Bank> all();

  /**
   * Adds a bank to the repository.
   *
   * @param bank the bank to add.
   */
  void add(Bank bank);

  /**
   * Removes the bank with the given name from the repository.
   *
   * @param bank the name of the bank to remove.
   * @throws NoSuchBankException if the bank with the given name does not exist.
   */
  void remove(String bank) throws NoSuchBankException;

  /**
   * Removes the given bank from the repository.
   *
   * @param bank the bank to remove.
   * @throws NoSuchBankException if the given bank does not exist.
   */
  void remove(Bank bank) throws NoSuchBankException;

  /**
   * Returns the bank with the given ID.
   *
   * @param bank the ID of the bank to return.
   * @return the bank with the given ID.
   * @throws NoSuchBankException if the bank with the given ID does not exist.
   */
  @NonNull
  Bank get(Short bank) throws NoSuchBankException;

  /**
   * Returns the bank with the given name.
   *
   * @param bank the name of the bank to return.
   * @return the bank with the given name.
   * @throws NoSuchBankException if the bank with the given name does not exist.
   */
  @NonNull
  Bank get(String bank) throws NoSuchBankException;
}