package me.macao.lab4.dto;

import lombok.NonNull;
import me.macao.lab4.percistence.CatColor;

import java.time.LocalDate;
import java.util.Collection;

public record CatUpdateDTO(
        @NonNull Long id,
        String name,
        String breed,
        CatColor color,
        LocalDate birthday,
        Collection<Long> friends,
        Collection<Long> reqsOut
) { }
