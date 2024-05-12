package me.macao.exception;

public class EmailCreateException
    extends Exception {

    public EmailCreateException(String s) { super(s); }

    @Override
    public String getMessage() { return super.getMessage(); }
}
