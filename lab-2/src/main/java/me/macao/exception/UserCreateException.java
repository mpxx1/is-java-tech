package me.macao.exception;

public class UserCreateException
    extends Exception {

    public UserCreateException(String message) {
        super(message);
    }

    @Override
    public String getMessage() { return super.getMessage(); }
}
