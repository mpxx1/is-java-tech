package me.macao.business.exception;

public class WrongAccountException
  extends Exception {

  public WrongAccountException() {}

  public WrongAccountException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
