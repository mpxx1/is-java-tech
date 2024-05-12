package me.macao.lab4.exception;

public class EmailCreateException
    extends Exception {

    public EmailCreateException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
