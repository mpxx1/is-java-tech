package me.macao.business.exception;

public class TransactionException
        extends Exception {

  public TransactionException() {}

  public TransactionException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}


