package me.macao.business.model.interfaces;

/**
 * An interface representing a deposit percent.
 */
public interface DepositPercent {

  /**
   * Returns the lower bound of the deposit percent.
   *
   * @return the lower bound of the deposit percent.
   */
  double start();

  /**
   * Returns the upper bound of the deposit percent.
   *
   * @return the upper bound of the deposit percent.
   */
  double end();

  /**
   * Returns the percent of the deposit percent.
   *
   * @return the percent of the deposit percent.
   */
  double percent();
}
