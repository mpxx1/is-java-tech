package me.macao.exception;

public class DBTimeoutException
    extends Exception {

    public DBTimeoutException() {}

    public DBTimeoutException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
