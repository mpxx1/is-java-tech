package me.macao.console.impl;

import lombok.NonNull;
import me.macao.business.exception.*;
import me.macao.business.model.enums.BankAccountType;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.DepositRange;
import me.macao.business.model.interfaces.Message;
import me.macao.business.service.interfaces.Bank;
import me.macao.console.interfaces.SessionApi;
import me.macao.console.interfaces.SessionState;

import java.util.Collection;

/**
 * A class representing a session API implementation.
 */
public class SessionApiImpl
        implements SessionApi {

  private SessionState state = new SessionStateLogout();

  @Override
  public void login(@NonNull String bankName, long userId, short passwd)
          throws LoginException, NotPermitedException {

    state = state.login(bankName, userId, passwd);
  }

  @Override
  public void adminLogin(short id, short passwd)
          throws LoginException, NotPermitedException {

    state = state.adminLogin(id, passwd);
  }

  @Override
  public void logout() {
    state = state.logout();
  }

  @Override
  public @NonNull Collection<@NonNull BankAccount> getAccounts()
    throws NotPermitedException {

    return state.getAccounts();
  }

  @Override
  public @NonNull BankAccount createAccount(BankAccountType accountType, int days, double amount)
          throws NotPermitedException, AccountCreateException {

    return state.createAccount(
            accountType,
            days,
            amount
    );
  }

  @Override
  public void withdraw(long accountId, double amount)
          throws TransactionException, NotPermitedException, WrongAccountException {

    state.withdraw(accountId, amount);
  }

  @Override
  public void refill(long accountId, double amount)
          throws TransactionException, NotPermitedException, WrongAccountException {

    state.refill(accountId, amount);
  }

  @Override
  public void transfer(long accountFrom, long accountTo, double amount)
          throws TransactionException, NotPermitedException, WrongAccountException {

    state.transfer(accountFrom, accountTo, amount);
  }

  @Override
  public @NonNull Collection<Message> getInfo() throws NotPermitedException {

    return state.getInfo();
  }

  @Override
  public void clearInfo() throws NotPermitedException {

    state.clearInfo();
  }

  @Override
  public Bank addBank(@NonNull String name, double transferLimit, double withdrawLimit, double debitPercent, @NonNull DepositRange depositRange, double creditLimit, double commission) throws BankCreateException, NotPermitedException {

    return state.addBank(
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
  public void notifyBanks() throws NotPermitedException {

    state.notifyBanks();
  }
}
