package me.macao.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMFactory {
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getInstance() {
        if (emf == null) {
            emf = Persistence
                    .createEntityManagerFactory("default");
        }

        return emf;
    }
}
