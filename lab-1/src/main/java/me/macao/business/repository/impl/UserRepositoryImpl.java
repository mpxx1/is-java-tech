package me.macao.business.repository.impl;

import lombok.NonNull;
import me.macao.business.exception.NoSuchBankException;
import me.macao.business.exception.NoSuchUserException;
import me.macao.business.model.interfaces.User;
import me.macao.business.repository.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * A class representing a repository of users.
 */
public class UserRepositoryImpl
  implements UserRepository {

  private final Collection<User> users = new ArrayList<>();

  @Override
  public @NonNull Collection<@NonNull User> all() {
    return users;
  }

  @Override
  public void add(User user) {
    users.add(user);
  }

  @Override
  public void remove(User user)
          throws NoSuchUserException {

    try {

      users.remove(user);
    } catch (NoSuchElementException e) {

      throw new NoSuchUserException();
    }
  }

  @Override
  public void remove(long userId)
          throws NoSuchUserException {

    User userToRemove;

    try {
      userToRemove = users
              .stream()
              .filter(user -> user.getId() == userId)
              .toList()
              .getFirst();
    } catch (NoSuchElementException e) {

      throw new NoSuchUserException();
    }

    users.remove(userToRemove);
  }

  @Override
  public @NonNull User get(long userId)
          throws NoSuchUserException {

    try {

      return users
              .stream()
              .filter(u -> u.getId() == userId)
              .toList()
              .getFirst();
    } catch (NoSuchElementException e) {

      throw new NoSuchUserException();
    }
  }
}
