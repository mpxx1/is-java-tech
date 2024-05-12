package me.macao.lab4.service;

import lombok.AllArgsConstructor;
import me.macao.lab4.dto.AuthenticationResponse;
import me.macao.lab4.dto.UserCreateDTO;
import me.macao.lab4.dto.UserInitDTO;
import me.macao.lab4.dto.UserLoginDTO;
import me.macao.lab4.exception.EmailCreateException;
import me.macao.lab4.exception.InvalidOperationException;
import me.macao.lab4.exception.ObjectNotFoundException;
import me.macao.lab4.exception.PasswordCreateException;
import me.macao.lab4.percistence.User;
import me.macao.lab4.percistence.UserDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserDao userDao;
    private final JwtService jwtService;
    private final CryptoService cryptoService;

    public AuthenticationResponse register(UserInitDTO regDto)
            throws PasswordCreateException, EmailCreateException, InvalidOperationException {

        if (userDao.findByEmail(regDto.email()).isPresent())
            throw new InvalidOperationException("Email is already taken");

        var salt = cryptoService.generateSalt();

        cryptoService.passVerify(regDto.password());
        cryptoService.mailVerify(regDto.email());

        User user = userDao.create(
                new UserCreateDTO(
                        regDto.email(),
                        cryptoService.encode(regDto.password(), salt),
                        salt,
                        regDto.role()
                )
        );
        var token = jwtService.generateToken(user, new HashMap<>(

        ));

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(UserLoginDTO loginDto)
        throws ObjectNotFoundException {

        Optional<User> user = userDao.findByEmail(loginDto.email());

        if (user.isEmpty())
            throw new ObjectNotFoundException("Wrong email or password");

        var salt = user.get().getSalt();

        if (!cryptoService.verify(loginDto.password(), salt, user.get().getPassword()))
            throw new ObjectNotFoundException("Wrong email or password");

        var token = jwtService.generateToken(user.get(), new HashMap<>());

        return new AuthenticationResponse(token);
    }
}
