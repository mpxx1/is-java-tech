package me.macao.business.repository.interfaces;

import lombok.NonNull;
import me.macao.business.exception.NoSuchUserException;
import me.macao.business.model.interfaces.User;

import java.util.Collection;

/**
 * An interface representing a repository of users.
 */
public interface UserRepository {

  /**
   * Returns all users in the repository.
   *
   * @return a collection of all users in the repository.
   */
  @NonNull
  Collection<@NonNull User> all();

  /**
   * Adds a user to the repository.
   *
   * @param user the user to add.
   */
  void add(User user);

  /**
   * Removes the given user from the repository.
   *
   * @param user the user to remove.
   * @throws NoSuchUserException if the given user does not exist.
   */
  void remove(User user) throws NoSuchUserException;

  /**
   * Removes the user with the given ID from the repository.
   *
   * @param userId the ID of the user to remove.
   * @throws NoSuchUserException if the user with the given ID does not exist.
   */
  void remove(long userId) throws NoSuchUserException;

  /**
   * Returns the user with the given ID.
   *
   * @param userId the ID of the user to return.
   * @return the user with the given ID.
   * @throws NoSuchUserException if the user with the given ID does not exist.
   */
  @NonNull
  User get(long userId) throws NoSuchUserException;
}