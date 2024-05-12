package me.macao.business.model.interfaces;

import lombok.NonNull;

/**
 * An interface representing a message.
 */
public interface Message {

  /**
   * Returns the body of the message.
   *
   * @return the body of the message.
   */
  @NonNull String body();
}
