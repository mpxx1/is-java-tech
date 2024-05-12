package me.macao.console.impl;

import lombok.NonNull;
import me.macao.business.exception.*;
import me.macao.business.model.enums.BankAccountType;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.DepositRange;
import me.macao.business.service.impl.CentralBankImpl;
import me.macao.business.service.interfaces.CentralBank;
import me.macao.console.interfaces.SessionState;
import me.macao.business.model.interfaces.Message;
import me.macao.business.model.interfaces.User;
import me.macao.business.service.interfaces.Bank;

import java.util.Collection;

/**
 * A class representing a logout session state implementation.
 */
public class SessionStateLogout
  implements SessionState {

  @Override
  public @NonNull SessionState login(@NonNull String bankName, long userId, short passwd)
          throws LoginException {

    User user;
    Bank bank;
    CentralBank cb = CentralBankImpl.getInstance();

    try {
      bank = cb.getBankByName(bankName);

      user = bank.getUser(userId, passwd);
    } catch (NoSuchBankException e) {

      throw new LoginException("No such bank, wrong name");
    } catch (NoSuchUserException e) {

      throw new LoginException("No such user, wrong id or passwd");
    }

    return new SessionStateLogin(bank, user);
  }

  @Override
  public @NonNull SessionState adminLogin(short id, short passwd)
          throws LoginException, NotPermitedException {

    if (id != 0 || passwd != 1111)
      throw new LoginException("incorrect id or passwd");

    return new SessionStateCbAdmin();
  }

  @Override
  public @NonNull SessionState logout() {
    return this;
  }

  @Override
  public @NonNull Collection<@NonNull BankAccount> getAccounts()
          throws NotPermitedException {

    throw new NotPermitedException();
  }

  @Override
  public @NonNull BankAccount
  createAccount(BankAccountType accountType, int days, double amount)
          throws NotPermitedException {

    throw new NotPermitedException();
  }

  @Override
  public void withdraw(long accountId, double amount)
          throws NotPermitedException {

    throw new NotPermitedException();
  }

  @Override
  public void refill(long accountId, double amount)
          throws NotPermitedException {

    throw new NotPermitedException();
  }

  @Override
  public void transfer(long accountFrom, long accountTo, double amount)
          throws NotPermitedException {

    throw new NotPermitedException();
  }

  @Override
  public @NonNull Collection<Message> getInfo()
    throws NotPermitedException {

    throw new NotPermitedException();
  }

  @Override
  public void clearInfo() throws NotPermitedException {

    throw new NotPermitedException();
  }

  @Override
  public Bank addBank(@NonNull String name, double transferLimit, double withdrawLimit, double debitPercent, @NonNull DepositRange depositRange, double creditLimit, double commission) throws BankCreateException, NotPermitedException {
    throw new NotPermitedException();
  }

  @Override
  public void notifyBanks() throws NotPermitedException {
    throw new NotPermitedException();
  }
}
