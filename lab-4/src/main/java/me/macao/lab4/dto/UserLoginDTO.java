package me.macao.lab4.dto;

import lombok.NonNull;

public record UserLoginDTO(
        @NonNull String email,
        @NonNull String password
) { }
