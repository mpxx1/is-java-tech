package me.macao.lab4.dto;

import lombok.NonNull;
import me.macao.lab4.percistence.UserRole;

public record UserInitDTO(
        @NonNull String email,
        @NonNull String password,
        @NonNull UserRole role
) { }
