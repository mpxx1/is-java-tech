package me.macao.crypto;

import lombok.NonNull;
import me.macao.exception.EmailCreateException;

import java.util.regex.Pattern;

public class EmailVerifier {

    public void verify(@NonNull String email)
            throws EmailCreateException {
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        boolean isValid = Pattern
                .compile(regex)
                .matcher(email)
                .matches();

        if (!isValid)
            throw new EmailCreateException("Incorrect email, must be like <username>@<domain.com>");
    }
}
