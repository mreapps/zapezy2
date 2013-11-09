package com.mreapps.zapezy.dao.repository.dflt;

import com.mreapps.zapezy.dao.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Default implementation for jpa repositories
 * @param <T> The entity the repository handles
 */
public abstract class DefaultCrudRepository<T> implements CrudRepository<T>
{
    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> type;

    protected DefaultCrudRepository()
    {
        ParameterizedType genericType = (ParameterizedType) this.getClass().getGenericSuperclass();
        //noinspection unchecked
        this.type = (Class<T>) genericType.getActualTypeArguments()[0];
    }

    protected T findSingle(CriteriaQuery<T> cq)
    {
        // uses two as max result to reveal if there are non unique searches performed
        List<T> result = findMany(cq, 0, 2);
        if (result == null || result.size() == 0)
        {
            return null;
        } else if (result.size() == 1)
        {
            return result.get(0);
        }
        throw new NonUniqueResultException("Multiple entities returned from findSingle");
    }

    protected List<T> findMany(CriteriaQuery<T> cq, int startPosition, int maxResult)
    {
        TypedQuery<T> typedQuery = entityManager.createQuery(cq);
        typedQuery.setFirstResult(startPosition);
        typedQuery.setMaxResults(maxResult);

        return typedQuery.getResultList();
    }

    @Override
    public T get(long id)
    {
        return entityManager.find(type, id);
    }

    @Override
    public final T merge(T entity)
    {
        return entityManager.merge(entity);
    }

    @Override
    public final void persist(T entity)
    {
        entityManager.persist(entity);
    }

    @Override
    public final void remove(T entity)
    {
        entityManager.remove(entity);
    }
}
