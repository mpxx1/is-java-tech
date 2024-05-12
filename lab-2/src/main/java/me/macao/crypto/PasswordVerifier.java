package me.macao.crypto;

import lombok.NonNull;
import me.macao.exception.PasswordCreateException;

public class PasswordVerifier {

    public void verify(@NonNull String password)
            throws PasswordCreateException {

        if (password.length() < 8)
            throw new PasswordCreateException("Password must be at least 8 characters");

        int upper = 0;
        int lower = 0;
        int digits = 0;
        int special = 0;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                ++upper;

            if (Character.isLowerCase(c))
                ++lower;

            if (Character.isDigit(c))
                ++digits;

            else
                ++special;
        }

        if (!(upper > 0 && lower > 3 && digits > 0 && special > 0))
            throw new PasswordCreateException(
                    "Password must contain at least one upper, digit and special character, four lower"
            );
    }
}
