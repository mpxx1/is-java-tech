package me.macao.lab4.dto;

import lombok.NonNull;
import me.macao.lab4.percistence.UserRole;

import java.time.LocalDate;

public record UserUpdateDTO(
        @NonNull Long id,
        String email,
        String password,
        String name,
        LocalDate birthday
) { }
