package me.macao.dao;

import java.io.Serializable;

public class CatDao<
        Integer,
        Cat extends Serializable
        >
        extends GenericDaoImpl<Integer, Cat> {

    public CatDao(Class<Cat> clazz) {
        super(clazz);
    }
}
