package me.macao.console.impl;

import lombok.NonNull;
import me.macao.business.exception.*;
import me.macao.business.model.enums.BankAccountType;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.DepositRange;
import me.macao.business.model.interfaces.Message;
import me.macao.business.service.impl.CentralBankImpl;
import me.macao.business.service.interfaces.Bank;
import me.macao.business.service.interfaces.CentralBank;
import me.macao.console.interfaces.SessionState;

import java.util.Collection;

/**
 * A class representing a session state implementation of central bank admin.
 */
public class SessionStateCbAdmin
        implements SessionState {

  private final CentralBank centralBank = CentralBankImpl.getInstance();

  @Override
  public @NonNull SessionState login(@NonNull String bank, long userId, short passwd) throws NotPermitedException {
    throw new NotPermitedException();
  }

  @Override
  public @NonNull SessionState adminLogin(short id, short passwd) throws NotPermitedException {
    throw new NotPermitedException();
  }

  @Override
  public @NonNull SessionState logout() {
    return new SessionStateLogout();
  }

  @Override
  public @NonNull Collection<@NonNull BankAccount> getAccounts() throws NotPermitedException {
    throw new NotPermitedException();
  }

  @Override
  public @NonNull BankAccount createAccount(BankAccountType accountType, int days, double amount) throws NotPermitedException, AccountCreateException {
    throw new NotPermitedException();
  }

  @Override
  public void withdraw(long accountId, double amount) throws TransactionException, NotPermitedException {
    throw new NotPermitedException();
  }

  @Override
  public void refill(long accountId, double amount) throws TransactionException, NotPermitedException {
    throw new NotPermitedException();
  }

  @Override
  public void transfer(long accountFrom, long accountTo, double amount) throws NotPermitedException {
    throw new NotPermitedException();
  }

  @Override
  public @NonNull Collection<Message> getInfo() throws NotPermitedException {
    throw new NotPermitedException();
  }

  @Override
  public void clearInfo() throws NotPermitedException {
    throw new NotPermitedException();
  }

  public Bank addBank(
          @NonNull String name,
          double transferLimit,
          double withdrawLimit,
          double debitPercent,
          @NonNull DepositRange depositRange,
          double creditLimit, double commission
  ) throws BankCreateException {

    return centralBank.addBank(
            name,
            transferLimit,
            withdrawLimit,
            debitPercent,
            depositRange,
            creditLimit,
            commission
    );
  }

  @Override
  public void notifyBanks() {
    centralBank.notifyBanks();
  }
}
