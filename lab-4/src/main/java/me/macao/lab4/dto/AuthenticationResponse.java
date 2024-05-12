package me.macao.lab4.dto;

import lombok.NonNull;

public record AuthenticationResponse(
        @NonNull String token
) { }
