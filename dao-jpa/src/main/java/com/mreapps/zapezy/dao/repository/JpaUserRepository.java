package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.user.JpaUser;

/**
 * DAO for {@link com.mreapps.zapezy.dao.entity.user.JpaUser}
 */
public interface JpaUserRepository extends CrudRepository<JpaUser>
{
    /**
     * @param emailAddress The email address to look up
     * @return The user if found, null if not
     */
    JpaUser findByEmailAddress(String emailAddress);

    /**
     * @param emailConfirmationToken Unique token for a single user
     * @return The user with the token
     */
    JpaUser findByEmailConfirmationToken(String emailConfirmationToken);
}
