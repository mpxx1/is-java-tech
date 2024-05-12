package me.macao.business.exception;

public class LoginException
  extends Exception {

  public LoginException() {}

  public LoginException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
