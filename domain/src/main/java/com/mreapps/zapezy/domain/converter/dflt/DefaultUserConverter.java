package com.mreapps.zapezy.domain.converter.dflt;

import com.mreapps.zapezy.dao.entity.JpaUser;
import com.mreapps.zapezy.domain.converter.UserConverter;
import com.mreapps.zapezy.domain.entity.User;
import org.springframework.stereotype.Component;

/**
 * Default implementation of {@link UserConverter}
 */
@Component
public class DefaultUserConverter implements UserConverter
{
    @Override
    public JpaUser convertToDao(User user)
    {
        if (user == null)
        {
            return null;
        }

        JpaUser jpaUser = new JpaUser();
        jpaUser.setId(user.getId());
        jpaUser.setEmailAddress(user.getEmailAddress());
        jpaUser.setFirstName(user.getFirstName());
        jpaUser.setLastName(user.getLastName());

        return jpaUser;
    }

    @Override
    public User convertToDomain(JpaUser jpaUser)
    {
        if (jpaUser == null)
        {
            return null;
        }

        final User user;
        if (jpaUser.getId() == null)
        {
            user = new User();
        } else
        {
            user = new User(jpaUser.getId());
        }

        user.setEmailAddress(jpaUser.getEmailAddress());
        user.setFirstName(jpaUser.getFirstName());
        user.setLastName(jpaUser.getLastName());

        return user;
    }
}
