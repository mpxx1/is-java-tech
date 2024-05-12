package me.macao.exception;

public class SpecifyUserException
    extends Exception {

    public SpecifyUserException(String message) {
        super(message);
    }

    public SpecifyUserException() {}

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
