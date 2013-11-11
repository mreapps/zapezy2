package com.mreapps.zapezy.domain.validator.dflt;

import com.mreapps.zapezy.core.validation.DefaultValidationResult;
import com.mreapps.zapezy.core.validation.ValidationMessage;
import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.core.validation.ValidationSeverity;
import com.mreapps.zapezy.dao.entity.user.JpaUser;
import com.mreapps.zapezy.dao.repository.JpaUserRepository;
import com.mreapps.zapezy.domain.entity.User;
import com.mreapps.zapezy.domain.validator.UserValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Validates a {@link User}
 */
public class DefaultUserValidator implements UserValidator
{
    @Autowired
    private JpaUserRepository userRepository;

    @Override
    public ValidationResult validateUser(User user)
    {
        ValidationResult validationResult = new DefaultValidationResult();

        if (StringUtils.isBlank(user.getEmailAddress()))
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "email_address_must_be_set"));
        } else if (!EmailValidator.getInstance(true).isValid(user.getEmailAddress()))
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "email_address_is_not_valid"));
        } else
        {
            JpaUser existingUser = userRepository.findByEmailAddress(user.getEmailAddress());
            if (existingUser != null && !existingUser.getId().equals(user.getId()))
            {
                validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "email_address_is_already_in_use"));
            }
        }

        if (StringUtils.isBlank(user.getFirstName()))
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "first_name_must_be_set"));
        }

        if (StringUtils.isBlank(user.getLastName()))
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "last_name_must_be_set"));
        }

        return validationResult;
    }
}
