package me.macao.business.model.impl;

import lombok.NonNull;
import me.macao.business.model.interfaces.DepositPercent;
import me.macao.business.model.interfaces.DepositRange;

import java.util.List;

/**
 * A record representing a deposit range.
 */
public record DepositRangeImpl(@NonNull List<DepositPercent> ranges) implements DepositRange {
}