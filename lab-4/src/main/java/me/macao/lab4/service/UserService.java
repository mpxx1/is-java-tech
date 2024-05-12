package me.macao.lab4.service;

import me.macao.lab4.dto.*;
import me.macao.lab4.exception.InvalidOperationException;
import me.macao.lab4.exception.ObjectNotFoundException;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Collection<UserResponseDTO> getUsers();

    UserResponseDTO getUserById(Long id)
            throws ObjectNotFoundException;

    UserResponseDTO createUser(UserCreateDTO userDto)
            throws InvalidOperationException;

    UserResponseDTO updateUser(UserUpdateDTO userDto)
            throws ObjectNotFoundException, InvalidOperationException;

    void deleteUserById(Long id);

    Long getUserIdByEmail(String email) throws ObjectNotFoundException;
}
