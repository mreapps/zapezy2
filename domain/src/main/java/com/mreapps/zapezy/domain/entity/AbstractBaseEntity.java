package com.mreapps.zapezy.domain.entity;

import com.mreapps.zapezy.core.entity.BaseEntity;

/**
 * Abstract interface for all domain entities
 */
public abstract class AbstractBaseEntity implements BaseEntity
{
    private final Long id;

    /**
     * Default constructor. Sets final property id to null.
     */
    protected AbstractBaseEntity()
    {
        this.id = null;
    }

    /**
     *
     * @param id A unique id for the entity
     */
    protected AbstractBaseEntity(long id)
    {
        this.id = id;
    }

    /**
     *
     * @return A unique id for the entity
     */
    @Override
    public Long getId()
    {
        return id;
    }
}
