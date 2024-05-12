package me.macao.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import me.macao.exception.DBTimeoutException;
import me.macao.exception.InvalidOperationException;
import me.macao.exception.ObjectNotFoundException;

import java.io.Serializable;
import java.util.Collection;

public abstract class GenericDaoImpl<
        TIter,
        TObj extends Serializable
        >
        implements Dao<TIter, TObj> {

    protected final Class<TObj> clazz;

    protected EntityManager em = EMFactory
            .getInstance()
            .createEntityManager();

    public GenericDaoImpl(final Class<TObj> clazz) { this.clazz = clazz; }

    public TObj getById(final TIter id)
        throws ObjectNotFoundException {
        TObj target;

        em.clear();

        try {

            target = em.find(clazz, id);
            if (target == null)
                throw new ObjectNotFoundException();

            em.detach(target);
            em.refresh(target);
        } catch (IllegalArgumentException e) {
            throw new ObjectNotFoundException("No such entity in database");
        }

        return target;
    }

    public Collection<TObj> getItems(int from, int count)
        throws ObjectNotFoundException, DBTimeoutException {
        Query q;

        em.clear();

        try {

            q = em.createQuery("from " + clazz.getName(), clazz);
            q.setFirstResult(from);
            q.setMaxResults(count);
        } catch (IllegalArgumentException e) {

            throw new ObjectNotFoundException("Illegal argument");
        }

        try {

            return q.getResultList();
        } catch (Exception e) {

            throw new DBTimeoutException(e.getMessage());
        }
    }

    public Collection<TObj> findAll()
            throws ObjectNotFoundException, DBTimeoutException {

        em.clear();

        try {

            return em
                    .createQuery("from " + clazz.getName(), clazz)
                    .getResultList();
        } catch (IllegalArgumentException e) {

            throw new ObjectNotFoundException("Illegal argument");
        } catch (Exception e) {

            throw new DBTimeoutException(e.getMessage());
        }
    }

    public TObj create(final TObj entity)
            throws InvalidOperationException, ObjectNotFoundException {

        em.clear();

        try {

            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();

            em.refresh(entity);
        } catch (IllegalArgumentException e) {

            throw new ObjectNotFoundException(e.getMessage());
        } catch (Exception e) {

            throw new InvalidOperationException(e.getMessage());
        }

        return entity;
    }

    public TObj update(final TObj entity)
            throws InvalidOperationException, ObjectNotFoundException {

        em.clear();

        try {

            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();

            em.refresh(entity);
        } catch (IllegalArgumentException e) {

            throw new ObjectNotFoundException(e.getMessage());
        } catch (Exception e) {

            throw new InvalidOperationException(e.getMessage());
        }

        return entity;
    }

    public void delete(final TObj entity)
            throws InvalidOperationException, ObjectNotFoundException {

        em.clear();

        try {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {

            throw new ObjectNotFoundException(e.getMessage());
        } catch (Exception e) {

            throw new InvalidOperationException(e.getMessage());
        }
    }

    public void deleteById(final TIter id)
            throws InvalidOperationException, ObjectNotFoundException {

        em.clear();

        TObj target = getById(id);
        delete(target);
    }
}
