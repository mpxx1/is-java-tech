package me.macao.lab4.dto;

import lombok.NonNull;
import me.macao.lab4.percistence.UserRole;

public record UserCreateDTO(
        @NonNull String email,
        @NonNull String password,
        @NonNull String salt,
        @NonNull UserRole role
) {
}
