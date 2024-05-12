package me.macao.business.service.impl;

import lombok.NonNull;
import me.macao.business.exception.NoSuchBankException;
import me.macao.business.repository.impl.BankRepositoryImpl;
import me.macao.business.repository.interfaces.BankRepository;
import me.macao.business.model.interfaces.DepositRange;
import me.macao.business.service.interfaces.Bank;
import me.macao.business.service.interfaces.BankTransactionApi;
import me.macao.business.service.interfaces.CentralBank;
import me.macao.business.service.interfaces.CentralBankApi;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * A class representing a central bank implementation.
 */
public final class CentralBankImpl
  implements CentralBank {

  private static CentralBank INSTANCE;

  private CentralBankImpl() { }

  private final Map<Short, BankTransactionApi> apiMap = new TreeMap<>();
  private final BankRepository bankRepo = BankRepositoryImpl.getInstance();
  private final Random rand = new Random();

  private short generateToken() {
    int num = 0;

    while (num < 1000) {
      num = rand.nextInt(9999);
    }

    return (short) num;
  }

  @Override
  public @NonNull Bank addBank(
          @NonNull String name,
          double transferLimit,
          double withdrawLimit,
          double debitPercent,
          @NonNull DepositRange depositRange,
          double creditLimit,
          double commission
  ) {
    Bank bank = BankImpl
            .builder()
            .id(generateToken())
            .name(name)
            .cb(new CentralBankApiImpl(CentralBankImpl.getInstance()))
            .transferLimit(transferLimit)
            .withdrawLimit(withdrawLimit)
            .debitPercent(debitPercent)
            .depositRange(depositRange)
            .creditLimit(creditLimit)
            .creditCommission(commission)
            .build();

    bankRepo.add(bank);
    apiMap.put(
            bank.getId(), new BankTransactionApiImpl(bank)
    );

    return bank;
  }

  @Override
  public void notifyBanks() {
    bankRepo
            .all()
            .forEach(bank -> {
              bank.updateCommission();
              bank.updatePayment();
            });
  }

  @Override
  public @NonNull BankTransactionApi getBankById(short id)
          throws NoSuchBankException {
    try {

      return apiMap.get(id);
    } catch (NullPointerException e) {

      throw new NoSuchBankException();
    }
  }

  @Override
  public @NonNull Bank getBankByName(@NonNull String bank)
          throws NoSuchBankException {

    return bankRepo.get(bank);
  }

  public static CentralBank getInstance() {
    if (INSTANCE == null)
      INSTANCE = new CentralBankImpl();

    return INSTANCE;
  }
}
