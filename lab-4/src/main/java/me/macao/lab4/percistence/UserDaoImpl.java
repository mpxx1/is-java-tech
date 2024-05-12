package me.macao.lab4.percistence;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import me.macao.lab4.dto.*;
import me.macao.lab4.exception.ObjectNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDaoImpl
    implements UserDao {

    private final JpaUserDao userDao;

    public Optional<User> findByEmail(@NonNull String email) {
        return userDao
                .findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public Collection<User> findAll() {
        return userDao.findAll();
    }

    public User findById(Long id)
            throws ObjectNotFoundException {
        return userDao
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No user with id " + id));
    }

    public User create(UserCreateDTO userDto) {

        return userDao.save(
                User
                        .builder()
                        .email(userDto.email())
                        .password(userDto.password())
                        .salt(userDto.salt())
                        .role(userDto.role())
                        .build()
        );
    }

    @Transactional
    public User update(UserUpdateDTO userDto)
        throws ObjectNotFoundException {
        User user = userDao
                .findById(userDto.id())
                .orElseThrow(() -> new ObjectNotFoundException("No user with id " + userDto.id()));

        if (userDto.email() != null)
            user.setEmail(userDto.email());

        if (userDto.password() != null)
            user.setPassword(userDto.password());

        if (userDto.name() != null)
            user.setName(userDto.name());

        if (userDto.birthday() != null)
            user.setBirthday(userDto.birthday());

        return user;
    }

    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}
