package me.macao.exception;

public class InvalidOperationException
    extends Exception {

    public InvalidOperationException() {}

    public InvalidOperationException(String message) { super(message); }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
