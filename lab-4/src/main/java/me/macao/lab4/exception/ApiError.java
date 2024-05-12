package me.macao.lab4.exception;

import java.time.LocalDateTime;

public record ApiError(
    String path,
    String message,
    int statusCode,
    LocalDateTime timestamp
) { }
