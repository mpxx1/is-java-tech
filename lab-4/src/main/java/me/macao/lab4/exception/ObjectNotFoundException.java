package me.macao.lab4.exception;

public class ObjectNotFoundException
    extends Exception {
    public ObjectNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
