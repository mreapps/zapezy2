package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.user.JpaRole;

import java.util.List;

/**
 * DAO for {@link JpaRole}
 */
public interface JpaRoleRepository extends CrudRepository<JpaRole>
{
    /**
     * @param code The code to look up
     * @return The role with the supplied code
     */
    JpaRole findByCode(String code);

    /**
     * @return All roles
     */
    List<JpaRole> findAll();
}
