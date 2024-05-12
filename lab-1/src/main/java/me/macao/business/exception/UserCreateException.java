package me.macao.business.exception;

public class UserCreateException
  extends Exception {

  public UserCreateException() {}

  public UserCreateException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
