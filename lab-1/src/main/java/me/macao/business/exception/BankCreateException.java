package me.macao.business.exception;

public class BankCreateException
  extends Exception {

  public BankCreateException() {}

  public BankCreateException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
