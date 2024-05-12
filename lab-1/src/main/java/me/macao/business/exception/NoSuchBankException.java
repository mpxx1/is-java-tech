package me.macao.business.exception;

public class NoSuchBankException
        extends Exception {

  public NoSuchBankException() {}

  public NoSuchBankException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
