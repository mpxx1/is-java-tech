package me.macao.business.repository.interfaces;

import lombok.NonNull;
import me.macao.business.exception.NoSuchAccountException;
import me.macao.business.exception.NoSuchUserException;
import me.macao.business.model.interfaces.BankAccount;

import java.util.Collection;

/**
 * An interface representing a repository of bank accounts.
 */
public interface AccountRepository {

  /**
   * Returns all bank accounts in the repository.
   *
   * @return a collection of all bank accounts in the repository.
   */
  @NonNull
  Collection<@NonNull BankAccount> all();

  /**
   * Adds a bank account to the repository.
   *
   * @param userId the ID of the user to whom the bank account belongs.
   * @param account the bank account to add.
   */
  void add(long userId, BankAccount account);

  /**
   * Returns the bank account with the given ID.
   *
   * @param accountId the ID of the bank account to return.
   * @return the bank account with the given ID.
   * @throws NoSuchAccountException if the bank account with the given ID does not exist.
   */
  @NonNull
  BankAccount getAccount(long accountId) throws NoSuchAccountException;

  /**
   * Removes the bank account with the given ID from the repository.
   *
   * @param accountId the ID of the bank account to remove.
   * @throws NoSuchAccountException if the bank account with the given ID does not exist.
   */
  void remove(long accountId) throws NoSuchAccountException;

  /**
   * Returns all bank accounts belonging to the user with the given ID.
   *
   * @param userId the ID of the user whose bank accounts to return.
   * @return a collection of all bank accounts belonging to the user with the given ID.
   * @throws NoSuchUserException if the user with the given ID does not exist.
   */
  @NonNull
  Collection<@NonNull BankAccount> get(long userId) throws NoSuchUserException;
}