package me.macao.lab4.service;

import lombok.AllArgsConstructor;
import me.macao.lab4.dto.UserCreateDTO;
import me.macao.lab4.dto.UserResponseDTO;
import me.macao.lab4.dto.UserUpdateDTO;
import me.macao.lab4.exception.InvalidOperationException;
import me.macao.lab4.exception.ObjectNotFoundException;
import me.macao.lab4.percistence.UserDao;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
@AllArgsConstructor
public class UserServiceImpl
    implements UserService {

    private final UserDao userDao;
    private final DTOMapper dtoMapper;

    public Collection<UserResponseDTO> getUsers() {
        return userDao
                .findAll()
                .stream()
                .map(dtoMapper::userToResponseDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id)
            throws ObjectNotFoundException {

        var user = userDao.findById(id);

        return dtoMapper.userToResponseDTO(user);
    }

    public UserResponseDTO createUser(UserCreateDTO userDto)
            throws InvalidOperationException {

        if (userDao.findByEmail(userDto.email()).isPresent())
            throw new InvalidOperationException("Email is already taken");

        var user = userDao.create(userDto);

        return dtoMapper.userToResponseDTO(user);
    }

    public UserResponseDTO updateUser(UserUpdateDTO userDto)
        throws ObjectNotFoundException, InvalidOperationException {

        if (userDto.email() != null && userDao.findByEmail(userDto.email()).isPresent())
            throw new InvalidOperationException("Email is already taken");

        // todo check password

        var user = userDao.update(userDto);

        return dtoMapper.userToResponseDTO(user);
    }

    public void deleteUserById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public Long getUserIdByEmail(String email)
            throws ObjectNotFoundException {

        var user = userDao.findByEmail(email);

        if (user.isEmpty())
            throw new ObjectNotFoundException("User not found");

        return user.get().getId();
    }
}
