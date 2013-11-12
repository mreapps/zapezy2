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

        validateEmailAddress(validationResult, user.getId(), user.getEmailAddress());
        validateFirstName(validationResult, user.getFirstName());
        validateLastName(validationResult, user.getLastName());
        validateRole(validationResult, user.getRole());

        return validationResult;
    }

    private void validateEmailAddress(ValidationResult validationResult, Long userId, String emailAddress)
    {
        if (StringUtils.isBlank(emailAddress))
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "email_address_must_be_set"));
        } else if (emailAddress.length() > JpaUser.MAX_EMAIL_LENGTH)
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "email_address_cannot_contain_more_than_x_chars", JpaUser.MAX_EMAIL_LENGTH));
        } else if (!EmailValidator.getInstance(true).isValid(emailAddress))
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "email_address_is_not_valid"));
        } else
        {
            JpaUser existingUser = userRepository.findByEmailAddress(emailAddress);
            if (existingUser != null && !existingUser.getId().equals(userId))
            {
                validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "email_address_is_already_in_use"));
            }
        }
    }

    private void validateFirstName(ValidationResult validationResult, String firstName)
    {
        if (StringUtils.isBlank(firstName))
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "first_name_must_be_set"));
        } else if (firstName.length() > JpaUser.MAX_FIRST_NAME_LENGTH)
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "first_name_cannot_contain_more_than_x_chars", JpaUser.MAX_FIRST_NAME_LENGTH));
        }
    }

    private void validateLastName(ValidationResult validationResult, String lastName)
    {

        if (StringUtils.isBlank(lastName))
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "last_name_must_be_set"));
        } else if (lastName.length() > JpaUser.MAX_LAST_NAME_LENGTH)
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "last_name_cannot_contain_more_than_x_chars", JpaUser.MAX_LAST_NAME_LENGTH));
        }
    }

    private void validateRole(ValidationResult validationResult, String role)
    {
        if (StringUtils.isBlank(role))
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "role_must_be_set"));
        }
    }
}
