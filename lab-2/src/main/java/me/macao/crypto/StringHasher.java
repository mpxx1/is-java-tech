package me.macao.crypto;

import lombok.NonNull;

public class StringHasher {

    public @NonNull String generate(@NonNull String input) {
        StringBuilder sb = new StringBuilder();

        sb.append(input.toLowerCase());
        sb.append("++!!");
        sb.append(input.toUpperCase());
        sb.append("~~~//?");

        return sb.toString();
    }
}
