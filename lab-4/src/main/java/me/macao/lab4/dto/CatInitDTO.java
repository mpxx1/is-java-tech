package me.macao.lab4.dto;

import lombok.NonNull;
import me.macao.lab4.percistence.CatColor;

import java.time.LocalDate;

public record CatInitDTO(
        @NonNull String name,
        @NonNull String breed,
        @NonNull CatColor color,
        @NonNull LocalDate birthday
) { }
