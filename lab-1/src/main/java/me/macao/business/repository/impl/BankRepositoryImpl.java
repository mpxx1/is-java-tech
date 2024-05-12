package me.macao.business.repository.impl;

import lombok.NonNull;
import me.macao.business.exception.NoSuchBankException;
import me.macao.business.repository.interfaces.BankRepository;
import me.macao.business.service.interfaces.Bank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * A class representing a repository of banks.
 */
public final class BankRepositoryImpl
  implements BankRepository {

  private static BankRepository INSTANCE;

  private final Collection<Bank> banks = new ArrayList<>();
  private BankRepositoryImpl() {}

  @Override
  public @NonNull Collection<@NonNull Bank> all() {
    return banks;
  }

  @Override
  public void add(Bank bank) {
    banks.add(bank);
  }

  @Override
  public void remove(String bank)
          throws NoSuchBankException {

    Bank bankToRemove;

    try {
       bankToRemove = banks
              .stream()
              .filter(
                      b -> b.getName().equals(bank)
              )
              .toList()
              .getFirst();

    } catch (NoSuchElementException e) {
      throw new NoSuchBankException();
    }

    banks.remove(bankToRemove);
  }

  @Override
  public void remove(Bank bank)
          throws NoSuchBankException {

    try {

      banks.remove(bank);
    } catch (NullPointerException ignored) {

      throw new NoSuchBankException();
    }
  }

  @Override
  public @NonNull Bank get(Short bank)
          throws NoSuchBankException {

    try {

      return banks
              .stream()
              .filter(b -> b.getId() == bank)
              .toList()
              .getFirst();
    } catch (NullPointerException e) {
      throw new NoSuchBankException();
    }
  }

  @Override
  public @NonNull Bank get(String bank)
          throws NoSuchBankException {

    try {

      return banks
              .stream()
              .filter(b -> b.getName().equals(bank))
              .toList()
              .getFirst();
    } catch (NullPointerException e) {
      throw new NoSuchBankException();
    }
  }

  public static BankRepository getInstance() {
    if (INSTANCE == null)
      INSTANCE = new BankRepositoryImpl();

    return INSTANCE;
  }
}
