package me.macao.lab4.exception;

public class InvalidOperationException
    extends Exception {

    public InvalidOperationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
