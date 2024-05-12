package me.macao.exception;

public class ObjectNotFoundException
    extends Exception {

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException() {}

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
