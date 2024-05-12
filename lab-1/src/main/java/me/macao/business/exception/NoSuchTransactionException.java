package me.macao.business.exception;

public class NoSuchTransactionException
  extends Exception {

  public NoSuchTransactionException() {}

  public NoSuchTransactionException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
