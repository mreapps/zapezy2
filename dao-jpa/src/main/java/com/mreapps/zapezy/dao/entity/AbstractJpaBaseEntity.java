package com.mreapps.zapezy.dao.entity;

import com.mreapps.zapezy.core.entity.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Mapped super class for all dao entities
 */
@MappedSuperclass
public abstract class AbstractJpaBaseEntity implements BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
}
