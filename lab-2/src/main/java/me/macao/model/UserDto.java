package me.macao.model;

import lombok.Builder;
import me.macao.crypto.EmailVerifier;
import me.macao.crypto.PasswordVerifier;
import me.macao.crypto.StringHasher;
import me.macao.exception.EmailCreateException;
import me.macao.exception.PasswordCreateException;

import java.time.LocalDate;

@Builder
public record UserDto(String email,
                      String passwd,
                      LocalDate bday,
                      String name) {

    public User toUser()
        throws EmailCreateException, PasswordCreateException {

        var mailVer = new EmailVerifier();
        var passVer = new PasswordVerifier();
        var hasher = new StringHasher();

        passVer.verify(passwd);
        mailVer.verify(email);

        return User
                .builder()
                .email(email)
                .passwdHash(hasher.generate(passwd))
                .birthday(bday)
                .name(name)
                .build();
    }
}
