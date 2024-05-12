package me.macao.business.repository.impl;

import lombok.NonNull;
import me.macao.business.model.interfaces.Message;
import me.macao.business.repository.interfaces.MessageRepository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class representing a repository of messages.
 */
public class MessageRepositoryImpl
  implements MessageRepository {
  private final Collection<Message> messages = new ArrayList<>();

  @Override
  public @NonNull Collection<Message> all() {
    return messages;
  }

  @Override
  public void add(Message message) {
    messages.add(message);
  }

  @Override
  public void clear() {
    messages.clear();
  }
}
