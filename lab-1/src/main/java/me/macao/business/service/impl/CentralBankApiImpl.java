package me.macao.business.service.impl;

import lombok.NonNull;
import me.macao.business.exception.NoSuchBankException;
import me.macao.business.service.interfaces.BankTransactionApi;
import me.macao.business.service.interfaces.CentralBank;
import me.macao.business.service.interfaces.CentralBankApi;

/**
 * A class representing a central bank API implementation for getting bank transaction API.
 */
public class CentralBankApiImpl
  implements CentralBankApi {

  private final @NonNull CentralBank cb;

  public CentralBankApiImpl(@NonNull CentralBank cb) {
    this.cb = cb;
  }

  @Override
  public @NonNull BankTransactionApi getBankById(short id)
          throws NoSuchBankException {

    return cb.getBankById(id);
  }
}
