package com.mreapps.zapezy.domain.service.dflt;

import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.dao.entity.JpaUser;
import com.mreapps.zapezy.dao.repository.JpaUserRepository;
import com.mreapps.zapezy.domain.converter.UserConverter;
import com.mreapps.zapezy.domain.entity.User;
import com.mreapps.zapezy.domain.service.UserService;
import com.mreapps.zapezy.domain.validator.UserValidator;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional(readOnly = true)
public class DefaultUserService implements UserService
{
    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserConverter userConverter;

    @Override
    public ValidationResult registerNewUser(String emailAddress, String firstName, String lastName)
    {
        Validate.notNull(emailAddress);

        final ValidationResult validationResult;

        User user = new User();
        user.setEmailAddress(emailAddress.toLowerCase());
        user.setFirstName(firstName);
        user.setLastName(lastName);

        validationResult = userValidator.validateUser(user);
        if (validationResult.isOk())
        {
            JpaUser jpaUser = userConverter.convertToDao(user);
            userRepository.persist(jpaUser);
        }
        return validationResult;
    }
}
