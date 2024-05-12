package me.macao.lab4.dto;

import lombok.NonNull;
import me.macao.lab4.percistence.UserRole;

import java.time.LocalDate;
import java.util.Collection;

public record UserResponseDTO(
        @NonNull Long id,
        @NonNull String email,
        @NonNull UserRole role,
        String name,
        LocalDate birthday,
        @NonNull Collection<Long> cats
) { }
