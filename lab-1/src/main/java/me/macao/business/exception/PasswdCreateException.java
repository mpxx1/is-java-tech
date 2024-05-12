package me.macao.business.exception;

public class PasswdCreateException
  extends Exception {

  public PasswdCreateException() {}

  public PasswdCreateException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
