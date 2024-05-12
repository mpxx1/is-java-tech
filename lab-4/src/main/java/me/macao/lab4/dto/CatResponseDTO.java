package me.macao.lab4.dto;

import lombok.NonNull;
import me.macao.lab4.percistence.CatColor;

import java.time.LocalDate;
import java.util.Collection;

public record CatResponseDTO(
        @NonNull Long ownerId,
        @NonNull Long catId,
        @NonNull String name,
        @NonNull String breed,
        @NonNull CatColor catColor,
        @NonNull LocalDate birthday,
        @NonNull Collection<Long> friends,
        @NonNull Collection<Long> reqsIn,
        @NonNull Collection<Long> reqsOut
) { }
