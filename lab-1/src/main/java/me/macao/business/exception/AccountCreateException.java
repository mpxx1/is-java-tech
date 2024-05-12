package me.macao.business.exception;

public class AccountCreateException
  extends Exception {

  public AccountCreateException() {}

  public AccountCreateException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
