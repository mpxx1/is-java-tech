package me.macao.dao;

import me.macao.exception.DBTimeoutException;
import me.macao.exception.InvalidOperationException;
import me.macao.exception.ObjectNotFoundException;

import java.io.Serializable;
import java.util.Collection;

public interface Dao<
        TIter,
        TObj extends Serializable
        > {

    TObj getById(final TIter id)
            throws ObjectNotFoundException;

    Collection<TObj> getItems(int from, int count)
            throws ObjectNotFoundException, DBTimeoutException;

    Collection<TObj> findAll()
            throws ObjectNotFoundException, DBTimeoutException;

    TObj create(final TObj entity)
            throws InvalidOperationException, ObjectNotFoundException;

    TObj update(final TObj entity)
            throws InvalidOperationException, ObjectNotFoundException;

    void delete(final TObj entity)
            throws InvalidOperationException, ObjectNotFoundException;

    void deleteById(final TIter id)
            throws InvalidOperationException, ObjectNotFoundException;
}
