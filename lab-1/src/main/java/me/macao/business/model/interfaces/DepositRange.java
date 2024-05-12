package me.macao.business.model.interfaces;

import lombok.NonNull;

import java.util.Collection;

/**
 * An interface representing a range of deposit percents.
 */
public interface DepositRange {

  /**
   * Returns the collection of deposit percents in this range.
   *
   * @return the collection of deposit percents in this range.
   */
  @NonNull
  Collection<DepositPercent> ranges();
}
