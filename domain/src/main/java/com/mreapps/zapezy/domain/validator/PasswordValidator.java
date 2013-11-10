package com.mreapps.zapezy.domain.validator;

import com.mreapps.zapezy.core.validation.ValidationResult;

/**
 * Validates if the password is strong enough and if password1 and password2 are equal
 */
public interface PasswordValidator
{
    /**
     * @param password1 The password to validate password strengh
     * @param password2 Re-type password. Used to detect mis-typing
     * @return Validation result with errors if the passwords contains errors
     */
    ValidationResult validatePasswords(String password1, String password2);
}
