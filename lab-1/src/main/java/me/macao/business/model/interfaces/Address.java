package me.macao.business.model.interfaces;

import lombok.NonNull;

/**
 * An interface representing an address.
 */
public interface Address {

  /**
   * Returns the street address.
   *
   * @return the street address.
   */
  @NonNull String street();
}
