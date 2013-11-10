package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.core.entity.BaseEntity;

/**
 * A repository handles crud operations towards a database
 *
 * @param <T> The type of entity the repository handles
 */
public interface CrudRepository<T extends BaseEntity>
{
    /**
     * @param id The entities unique id
     * @return The entity with the supplied id. Null if no match is found
     */
    T get(long id);

    /**
     * Perform database lookup and merge the java entity with changes from the database
     *
     * @param entity The entity to merge
     * @return The merged entity
     */
    T merge(T entity);

    /**
     * Mark the entity for persistence
     *
     * @param entity The entity to persist
     */
    void persist(T entity);

    /**
     * Remove the supplied entity from the database
     *
     * @param entity The entity to remove
     */
    void remove(T entity);
}
