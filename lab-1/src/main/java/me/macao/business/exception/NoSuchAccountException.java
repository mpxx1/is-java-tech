package me.macao.business.exception;

public class NoSuchAccountException
  extends Exception {

  public NoSuchAccountException() {}

  public NoSuchAccountException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
