package com.mreapps.zapezy.domain.validator.dflt;

import com.mreapps.zapezy.core.validation.DefaultValidationResult;
import com.mreapps.zapezy.core.validation.ValidationMessage;
import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.core.validation.ValidationSeverity;
import com.mreapps.zapezy.dao.entity.user.JpaUser;
import com.mreapps.zapezy.domain.validator.PasswordValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Default implementation of {@link PasswordValidator}
 */
public class DefaultPasswordValidator implements PasswordValidator
{


    @Override
    public ValidationResult validatePasswords(String password1, String password2)
    {
        ValidationResult validationResult = new DefaultValidationResult();

        if (StringUtils.isBlank(password1) || password1.trim().length() < JpaUser.MIN_PASSWORD_LENGTH)
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "password_must_contain_at_least_x_chars", JpaUser.MIN_PASSWORD_LENGTH));
        }
        else if (password1.trim().length() > JpaUser.MAX_PASSWORD_LENGTH)
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "password_cannot_contain_more_than_x_chars", JpaUser.MAX_PASSWORD_LENGTH));
        }

        if (!new EqualsBuilder().append(password1, password2).isEquals())
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "passwords_do_not_match"));
        }

        return validationResult;
    }
}
