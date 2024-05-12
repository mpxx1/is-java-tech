package me.macao.business.model.impl;

import me.macao.business.model.interfaces.DepositPercent;

/**
 * A record representing a deposit percent.
 */
public record DepositPercentImpl(double start, double end, double percent) implements DepositPercent {
}
