package me.macao.business.repository.impl;

import lombok.NonNull;
import me.macao.business.exception.NoSuchAccountException;
import me.macao.business.exception.NoSuchUserException;
import me.macao.business.repository.interfaces.AccountRepository;
import me.macao.business.model.interfaces.BankAccount;

import java.util.*;

/**
 * A class representing a repository of bank accounts.
 */
public class AccountRepositoryImpl
  implements AccountRepository {

  private final @NonNull Map<Long, List<BankAccount>> accounts = new HashMap<>();

  @Override
  public @NonNull Collection<@NonNull BankAccount> all() {
    return accounts
            .values()
            .stream()
            .flatMap(Collection::stream)
            .toList();
  }

  @Override
  public void add(long userId, BankAccount account) {
    try {

      var accsList = accounts.get(userId);

      accsList.add(account);
    } catch (NullPointerException e) {

      List<BankAccount> lst = new ArrayList<>();
      lst.add(account);

      accounts.put(userId, lst);
    }
  }

  @Override
  public @NonNull BankAccount
  getAccount(long accountId)
          throws NoSuchAccountException {

    BankAccount account;

    try {

      account = all()
              .stream()
              .filter(acc -> acc.getId() == accountId)
              .toList()
              .getFirst();
    } catch (NoSuchElementException e) {

      throw new NoSuchAccountException();
    }

    return account;
  }

  @Override
  public void remove(long accountId)
    throws NoSuchAccountException {
    BankAccount accToRemove;

    try {

      accToRemove = accounts
              .values()
              .stream()
              .flatMap(Collection::stream)
              .filter(a -> a.getId() == accountId)
              .toList()
              .getFirst();
    } catch (NoSuchElementException e) {

      throw new NoSuchAccountException();
    }
  }

  @Override
  public @NonNull Collection<@NonNull BankAccount>
  get(long userId)
          throws NoSuchUserException {

    try {
      return accounts.get(userId);

    } catch (NullPointerException e) {

      throw new NoSuchUserException();
    }

  }
}
