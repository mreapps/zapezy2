package com.mreapps.zapezy.domain.converter;

import com.mreapps.zapezy.dao.entity.JpaUser;
import com.mreapps.zapezy.domain.entity.User;

/**
 * Interface for converting users
 */
public interface UserConverter
{
    /**
     * @param user The user to convert
     * @return A jpa version of the domain user. Null if user is null
     */
    JpaUser convertToDao(User user);

    /**
     * @param jpaUser The user to convert
     * @return A domain version of the jpa user. Null if jpaUser is null
     */
    User convertToDomain(JpaUser jpaUser);
}
