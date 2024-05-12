package me.macao.business.exception;

public class NotPermitedException
  extends Exception {

  public NotPermitedException() {}

  public NotPermitedException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
