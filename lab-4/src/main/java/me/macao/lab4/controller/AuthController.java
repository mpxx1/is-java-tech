package me.macao.lab4.controller;

import lombok.AllArgsConstructor;
import me.macao.lab4.dto.AuthenticationResponse;
import me.macao.lab4.dto.UserInitDTO;
import me.macao.lab4.dto.UserLoginDTO;
import me.macao.lab4.exception.EmailCreateException;
import me.macao.lab4.exception.InvalidOperationException;
import me.macao.lab4.exception.ObjectNotFoundException;
import me.macao.lab4.exception.PasswordCreateException;
import me.macao.lab4.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/v0.1.0/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("signup")
    public AuthenticationResponse register(@RequestBody UserInitDTO regDto)
            throws InvalidOperationException, DateTimeParseException,
            EmailCreateException, PasswordCreateException {

        return authService.register(regDto);
    }

    @PostMapping("login")
    public AuthenticationResponse login(@RequestBody UserLoginDTO loginDto)
            throws ObjectNotFoundException {

        return authService.authenticate(loginDto);
    }
}
