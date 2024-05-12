package me.macao.lab4.service;

import me.macao.lab4.exception.EmailCreateException;
import me.macao.lab4.exception.PasswordCreateException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CryptoService {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final int SALT_LENGTH = 8;

    public void mailVerify(String email)
        throws EmailCreateException {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        if (!matcher.matches())
            throw new EmailCreateException("Invalid email");
    }

    public void passVerify(String rawPassword)
        throws PasswordCreateException {

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        if (rawPassword.length() < 8) {
            throw new PasswordCreateException("Password length must have at least 8 characters !!");
        }
        if (!specailCharPatten.matcher(rawPassword).find()) {
            throw new PasswordCreateException("Password must have at least one special character !!");
        }
        if (!UpperCasePatten.matcher(rawPassword).find()) {
            throw new PasswordCreateException("Password must have at least one uppercase character !!");
        }
        if (!lowerCasePatten.matcher(rawPassword).find()) {
            throw new PasswordCreateException("Password must have at least one lowercase character !!");
        }
        if (!digitCasePatten.matcher(rawPassword).find()) {
            throw new PasswordCreateException("Password must have at least one digit character !!");
        }
    }

    public String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String encode(String password, String salt) {

        String pepper;
        try {
            pepper = readPepper();
        } catch (IOException e) {
            pepper = "";
        }

        return encoder.encode(salt + password + pepper);
    }

    public boolean verify(String password, String salt, String hash) {
        String pepper;
        try {
            pepper = readPepper();
        } catch (IOException e) {
            pepper = "";
        }

        return encoder.matches(salt + password + pepper, hash);
    }

    private String readPepper()
            throws IOException {

        File file = new File("/Users/m/IdeaProjects/mpxx1/lab-4/src/main/resources/.pepper");
        if (!file.exists()) { return ""; }

        BufferedReader buffReader = new BufferedReader(new FileReader("/Users/m/IdeaProjects/mpxx1/lab-4/src/main/resources/.pepper"));
        String line = buffReader.readLine();
        buffReader.close();

        return line;
    }
}
