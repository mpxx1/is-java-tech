package me.macao.business.repository.interfaces;

import lombok.NonNull;
import me.macao.business.model.interfaces.Message;

import java.util.Collection;

/**
 * An interface representing a repository of messages.
 */
public interface MessageRepository {

  /**
   * Returns all messages in the repository.
   *
   * @return a collection of all messages in the repository.
   */
  @NonNull
  Collection<Message> all();

  /**
   * Adds a message to the repository.
   *
   * @param message the message to add.
   */
  void add(Message message);

  /**
   * Clears the repository of all messages.
   */
  void clear();
}
