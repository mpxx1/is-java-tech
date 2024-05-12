package me.macao.business.model.impl;

import lombok.NonNull;
import me.macao.business.model.interfaces.Message;

/**
 * A record representing a message.
 */
public record MessageImpl(@NonNull String body) implements Message {
}
