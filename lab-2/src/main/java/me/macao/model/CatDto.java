package me.macao.model;

import java.time.LocalDate;

public record CatDto(
    String name,
    LocalDate bday,
    String breed,
    CatColor color) {
}

