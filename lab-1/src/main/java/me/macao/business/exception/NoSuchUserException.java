package me.macao.business.exception;

public class NoSuchUserException
  extends Exception {

  public NoSuchUserException() {}

  public NoSuchUserException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
