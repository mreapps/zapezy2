package com.mreapps.zapezy.domain.validator.dflt;

import com.mreapps.zapezy.core.validation.DefaultValidationResult;
import com.mreapps.zapezy.core.validation.ValidationMessage;
import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.core.validation.ValidationSeverity;
import com.mreapps.zapezy.domain.validator.PasswordValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Default implementation of {@link PasswordValidator}
 */
public class DefaultPasswordValidator implements PasswordValidator
{
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 50;

    @Override
    public ValidationResult validatePasswords(String password1, String password2)
    {
        ValidationResult validationResult = new DefaultValidationResult();

        if (StringUtils.isBlank(password1) || password1.trim().length() < MIN_PASSWORD_LENGTH)
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "password_must_contain_at_least_x_chars", MIN_PASSWORD_LENGTH));
        }
        else if (password1.trim().length() > MAX_PASSWORD_LENGTH)
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "password_cannot_contain_more_than_x_chars", MAX_PASSWORD_LENGTH));
        }

        if (!new EqualsBuilder().append(password1, password2).isEquals())
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "passwords_do_not_match"));
        }

        return validationResult;
    }
}
