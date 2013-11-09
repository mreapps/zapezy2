package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.JpaUser;

/**
 * DAO for {@link com.mreapps.zapezy.dao.entity.JpaUser}
 */
public interface JpaUserRepository extends CrudRepository<JpaUser>
{
    /**
     * @param emailAddress The email address to look up
     * @return The user if found, null if not
     */
    JpaUser findByEmailAddress(String emailAddress);
}
