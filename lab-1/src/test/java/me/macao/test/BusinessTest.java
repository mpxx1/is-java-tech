package me.macao.test;

import me.macao.business.exception.AccountCreateException;
import me.macao.business.exception.BankCreateException;
import me.macao.business.exception.TransactionException;
import me.macao.business.model.enums.BankAccountType;
import me.macao.business.model.impl.*;
import me.macao.business.model.interfaces.BankAccount;
import me.macao.business.model.interfaces.User;
import me.macao.business.service.impl.BankConstsApiImpl;
import me.macao.business.service.impl.BankTransactionApiImpl;
import me.macao.business.service.impl.CentralBankImpl;
import me.macao.business.service.interfaces.Bank;
import me.macao.business.service.interfaces.BankConstsApi;
import me.macao.business.service.interfaces.CentralBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BusinessTest {

  private static final CentralBank cb = CentralBankImpl.getInstance();

  @Test
  public void BankCreationTest() {

    Bank bank = null;
    var depRange = new DepositRangeImpl(new ArrayList<>());

    try {

      bank = cb.addBank(
              "bank1",
              10,
              11,
              12,
              depRange,
              13,
              14
      );
    } catch (BankCreateException e) {

      System.err.println("Can not create bank, wrong parameters");
      System.exit(1);
    }

    Assertions.assertEquals("bank1", bank.getName());
    Assertions.assertEquals(10, bank.getTransferLimit());
    Assertions.assertEquals(11, bank.getWithdrawLimit());
    Assertions.assertEquals(12, bank.getDebitPercent());
    Assertions.assertEquals(depRange, bank.getDepositRange());
    Assertions.assertEquals(13, bank.getCreditLimit());
    Assertions.assertEquals(14, bank.getCreditCommission());
  }

  @Test
  public void UserCreationTest() {

    Bank bank = null;
    var depRange = new DepositRangeImpl(new ArrayList<>());

    try {

      bank = cb.addBank(
              "bank1",
              10,
              11,
              12,
              depRange,
              13,
              14
      );
    } catch (BankCreateException e) {

      System.exit(1);
    }

    User user = bank.createUser(
            "John",
            "Dow",
            null,
            null
    );

    Assertions.assertEquals("John", user.getName());
    Assertions.assertEquals("Dow", user.getSurname());
    Assertions.assertNull(user.getAddress());
    Assertions.assertNull(user.getPassport());
  }

  @Test
  public void AccountCreationTest() {

    Bank bank = null;
    var depRange = new DepositRangeImpl(new ArrayList<>());

    try {

      bank = cb.addBank(
              "bank1",
              10,
              11,
              12,
              depRange,
              13,
              14
      );
    } catch (BankCreateException e) {

      System.exit(1);
    }

    User user = bank.createUser(
            "John",
            "Dow",
            null,
            null
    );

    BankAccount account = null;

    try {

      account = bank.createAccount(
              user.getId(),
              BankAccountType.DEBIT,
              0,
              10_000
      );
    } catch (AccountCreateException e) {
      System.err.println("Can not create account\n" + e.getMessage());
      System.exit(1);
    }

    Assertions.assertEquals(10_000, account.getAmount());

    try {
      Assertions.assertEquals(12, account.getPercent());
    } catch (NoSuchFieldException ignored) {
    }
  }

  @Test
  public void WithdrawRefillTest() {

    Bank bank = null;
    var depRange = new DepositRangeImpl(new ArrayList<>());

    try {

      bank = cb.addBank(
              "bank1",
              10,
              3_000,
              12,
              depRange,
              13,
              14
      );
    } catch (BankCreateException e) {

      System.exit(1);
    }

    User user = bank.createUser(
            "John",
            "Dow",
            null,
            null
    );

    BankAccount account = null;

    try {

      account = bank.createAccount(
              user.getId(),
              BankAccountType.DEBIT,
              0,
              10_000
      );
    } catch (AccountCreateException e) {
      System.err.println("Can not create account\n" + e.getMessage());
      System.exit(1);
    }

    try {

      bank.execTransaction(
              new RefillTransaction(
                      account,
                      1_000
              )
      );
    } catch (TransactionException ignored) {
    }

    Assertions.assertEquals(11_000, account.getAmount());

    try {
      bank.execTransaction(
              new WithdrawTransaction(
                      account,
                      2_000,
                      new BankConstsApiImpl(bank),
                      user.getUserAccountType()
              )
      );
    } catch (TransactionException ignored) {
    }

    Assertions.assertEquals(9_000, account.getAmount());
  }

  @Test
  public void WithdrawRefillTest2() {

    Bank bank = null;
    var depRange = new DepositRangeImpl(new ArrayList<>());

    try {

      bank = cb.addBank(
              "bank1",
              10,
              3_000,
              12,
              depRange,
              13,
              14
      );
    } catch (BankCreateException e) {

      System.exit(1);
    }

    User user = bank.createUser(
            "John",
            "Dow",
            null,
            null
    );

    BankAccount account = null;

    try {

      account = bank.createAccount(
              user.getId(),
              BankAccountType.DEBIT,
              0,
              0
      );
    } catch (AccountCreateException e) {
      System.err.println("Can not create account\n" + e.getMessage());
      System.exit(1);
    }

    try {

      bank.execTransaction(
              new WithdrawTransaction(
                      account,
                      1_000,
                      new BankConstsApiImpl(bank),
                      user.getUserAccountType()
              )
      );
    } catch (TransactionException e) {

      Assertions.assertEquals("Can not withdraw more than you have!", e.getMessage());
    }

    try {
      bank.execTransaction(
              new RefillTransaction(
                      account,
                      -1_000
              )
      );
    } catch (TransactionException e) {

      Assertions.assertEquals("Negative amount!", e.getMessage());
    }

    try {
      bank.execTransaction(
              new RefillTransaction(
                      account,
                      3_000
              )
      );
    } catch (TransactionException ignored) {
    }

    Assertions.assertEquals(3_000, account.getAmount());
  }

  @Test
  public void TransferTest() {

    Bank bank1 = null;
    Bank bank2 = null;

    var depRange = new DepositRangeImpl(new ArrayList<>());

    try {

      bank1 = cb.addBank(
              "bank1",
              2_000,
              2_000,
              4,
              depRange,
              10_000,
              100
      );

      bank2 = cb.addBank(
              "bank2",
              2_000,
              2_000,
              4,
              depRange,
              10_000,
              100
      );
    } catch (BankCreateException e) {

      System.exit(1);
    }

    User user1 = bank1.createUser(
            "John",
            "Dow",
            null,
            null
    );

    User user2 = bank1.createUser(
            "John",
            "Snow",
            null,
            null
    );

    BankAccount account1 = null;
    BankAccount account2 = null;


    try {

      account1 = bank1.createAccount(
              user1.getId(),
              BankAccountType.DEBIT,
              0,
              11_000
      );

      account2 = bank2.createAccount(
              user1.getId(),
              BankAccountType.DEBIT,
              0,
              9_000
      );
    } catch (AccountCreateException e) {
      System.err.println("Can not create account\n" + e.getMessage());
      System.exit(1);
    }

    try {
      bank1.execTransaction(
              new TransferInst0Transaction(
                      account1,
                      account2.getId(),
                      2_000,
                      new BankTransactionApiImpl(bank2),
                      new BankConstsApiImpl(bank1),
                      user1.getUserAccountType()
              )
      );
    } catch (TransactionException ignored) {
    }

    Assertions.assertEquals(9_000, account1.getAmount());
    Assertions.assertEquals(11_000, account2.getAmount());
  }

  @Test
  public void notАllowedWithdrawTest() {

    Bank bank = null;
    var depRange = new DepositRangeImpl(new ArrayList<>());

    try {

      bank = cb.addBank(
              "bank1",
              100,
              100,
              3.8,
              depRange,
              10_000,
              200
      );
    } catch (BankCreateException e) {

      System.exit(1);
    }

    User user = bank.createUser(
            "John",
            "Dow",
            null,
            null
    );

    BankAccount account = null;

    try {

      account = bank.createAccount(
              user.getId(),
              BankAccountType.DEBIT,
              0,
              10_000
      );
    } catch (AccountCreateException e) {
      System.err.println("Can not create account\n" + e.getMessage());
      System.exit(1);
    }


    try {

      bank.execTransaction(
              new WithdrawTransaction(
                      account,
                      200,
                      new BankConstsApiImpl(bank),
                      user.getUserAccountType()
              )
      );
    } catch (TransactionException e) {

      Assertions.assertEquals(
              "DOUBTFUL User account, set passport to unlock withdraw limit",
              e.getMessage()
      );
    }

    user.setPassport((long) 401900554);

    try {

      bank.execTransaction(
              new WithdrawTransaction(
                      account,
                      2_000,
                      new BankConstsApiImpl(bank),
                      user.getUserAccountType()
              )
      );
    } catch (TransactionException ignored) {
    }

    Assertions.assertEquals(8_000, account.getAmount());
  }

  @Test
  public void negativeTransferTest() {
    Bank bank1 = null;
    Bank bank2 = null;

    var depRange = new DepositRangeImpl(new ArrayList<>());

    try {

      bank1 = cb.addBank(
              "bank1",
              2_000,
              2_000,
              4,
              depRange,
              10_000,
              100
      );

      bank2 = cb.addBank(
              "bank2",
              2_000,
              2_000,
              4,
              depRange,
              10_000,
              100
      );
    } catch (BankCreateException e) {

      System.exit(1);
    }

    User user1 = bank1.createUser(
            "John",
            "Dow",
            null,
            null
    );

    User user2 = bank1.createUser(
            "John",
            "Snow",
            null,
            null
    );

    BankAccount account1 = null;
    BankAccount account2 = null;


    try {

      account1 = bank1.createAccount(
              user1.getId(),
              BankAccountType.DEBIT,
              0,
              11_000
      );

      account2 = bank2.createAccount(
              user1.getId(),
              BankAccountType.DEBIT,
              0,
              9_000
      );
    } catch (AccountCreateException e) {
      System.err.println("Can not create account\n" + e.getMessage());
      System.exit(1);
    }

    try {

      bank1.execTransaction(
              new TransferInst0Transaction(
                      account1,
                      account2.getId(),
                      -2_000,
                      new BankTransactionApiImpl(bank2),
                      new BankConstsApiImpl(bank1),
                      user1.getUserAccountType()
              )
      );
    } catch (TransactionException e) {

      Assertions.assertEquals(
              "Negative amount!",
              e.getMessage()
      );
    }

    try {

      bank1.execTransaction(
              new TransferInst0Transaction(
                      account1,
                      account2.getId(),
                      3_000,
                      new BankTransactionApiImpl(bank2),
                      new BankConstsApiImpl(bank1),
                      user1.getUserAccountType()
              )
      );
    } catch (TransactionException e) {

      Assertions.assertEquals(
              "DOUBTFUL User account, set passport to unlock transfer limit",
              e.getMessage()
      );
    }

    user1.setPassport((long) 114433442);

    try {

      bank1.execTransaction(
              new TransferInst0Transaction(
                      account1,
                      account2.getId(),
                      3_000,
                      new BankTransactionApiImpl(bank2),
                      new BankConstsApiImpl(bank1),
                      user1.getUserAccountType()
              )
      );
    } catch (TransactionException ignored) {
    }

    Assertions.assertEquals(8_000, account1.getAmount());
    Assertions.assertEquals(12_000, account2.getAmount());
  }

  @Test
  public void depositWithdrawTest() {

    Bank bank = null;
    var depRange = new DepositRangeImpl(List.of(
            new DepositPercentImpl(0, 10_000, 4),
            new DepositPercentImpl(10_000, 1_000_000, 3.65)
    ));

    try {

      bank = cb.addBank(
              "bank1",
              1_000,
              1_000,
              3.9,
              depRange,
              10_000,
              300
      );
    } catch (BankCreateException e) {

      System.exit(1);
    }

    User user = bank.createUser(
            "John",
            "Dow",
            null,
            (long) 1010_10100
    );

    BankAccount account = null;

    try {

      account = bank.createAccount(
              user.getId(),
              BankAccountType.DEPOSIT,
              100,
              10_000
      );
    } catch (AccountCreateException e) {

      System.err.println("Can not create account\n" + e.getMessage());
      System.exit(1);
    }

    try {

      bank.execTransaction(
              new WithdrawTransaction(
                      account,
                      1_000,
                      new BankConstsApiImpl(bank),
                      user.getUserAccountType()
              )
      );
    } catch (TransactionException e) {

      Assertions.assertEquals(
              "Can not withdraw until finishing period",
              e.getMessage()
      );
    }

    for (int i = 0; i < 99; ++i)
      bank.updatePayment();

    try {

      bank.execTransaction(
              new WithdrawTransaction(
                      account,
                      1_000,
                      new BankConstsApiImpl(bank),
                      user.getUserAccountType()
              )
      );
    } catch (TransactionException e) {

      Assertions.assertEquals(
              "Can not withdraw until finishing period",
              e.getMessage()
      );
    }

    bank.updatePayment();

    try {

      bank.execTransaction(
              new WithdrawTransaction(
                      account,
                      1_000,
                      new BankConstsApiImpl(bank),
                      user.getUserAccountType()
              )
      );
    } catch (TransactionException ignored) {
    }

    Assertions.assertEquals(26048.13829421526, account.getAmount());
  }

  @Test
  public void creditAccountTest() {

    Bank bank = null;

    var depRange = new DepositRangeImpl(new ArrayList<>());

    try {

      bank = cb.addBank(
              "bank1",
              1_000,
              5_000,
              3.9,
              depRange,
              10_000,
              300
      );
    } catch (BankCreateException e) {

      System.exit(1);
    }

    User user = bank.createUser(
            "John",
            "Dow",
            null,
            (long) 1010_10100
    );

    BankAccount account = null;

    try {

      account = bank.createAccount(
              user.getId(),
              BankAccountType.CREDIT,
              0,
              0
      );
    } catch (AccountCreateException e) {

      System.err.println("Can not create account\n" + e.getMessage());
      System.exit(1);
    }

    try {

      bank.execTransaction(
            new WithdrawTransaction(
                    account,
                    4_000,
                    new BankConstsApiImpl(bank),
                    user.getUserAccountType()
            )
      );
    } catch (TransactionException ignored) {}

    Assertions.assertEquals(-4_000, account.getAmount());

    try {

      bank.execTransaction(
              new WithdrawTransaction(
                      account,
                      4_000,
                      new BankConstsApiImpl(bank),
                      user.getUserAccountType()
              )
      );
    } catch (TransactionException ignored) {}

    Assertions.assertEquals(-8_000, account.getAmount());

    try {

      bank.execTransaction(
              new RefillTransaction(
                      account,
                      1_000
              )
      );
    } catch (TransactionException ignored) {}

    Assertions.assertEquals(-7_000, account.getAmount());

    try {

      bank.execTransaction(
              new WithdrawTransaction(
                      account,
                      4_000,
                      new BankConstsApiImpl(bank),
                      user.getUserAccountType()
              )
      );
    } catch (TransactionException e) {

      Assertions.assertEquals(
              "Can not withdraw more than credit limit!",
              e.getMessage()
      );
    }
  }
}
