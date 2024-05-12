package me.macao.lab4.percistence;

import lombok.NonNull;
import me.macao.lab4.dto.UserCreateDTO;
import me.macao.lab4.dto.UserInitDTO;
import me.macao.lab4.dto.UserUpdateDTO;
import me.macao.lab4.exception.ObjectNotFoundException;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {

    Optional<User> findByEmail(@NonNull String email);

    Collection<User> findAll();

    User findById(Long id)
            throws ObjectNotFoundException;

    User create(UserCreateDTO userDto);

    User update(UserUpdateDTO userDto)
            throws ObjectNotFoundException;

    void deleteById(Long id);
}
