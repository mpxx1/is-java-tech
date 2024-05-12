package me.macao.dao;

import java.io.Serializable;

public class UserDao<Integer, User extends Serializable>
        extends GenericDaoImpl<Integer, User> {

    public UserDao(Class<User> clazz) {
        super(clazz);
    }
}
