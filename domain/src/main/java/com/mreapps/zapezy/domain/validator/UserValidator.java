package com.mreapps.zapezy.domain.validator;

import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.domain.entity.User;

/**
 * Validates user properties
 */
public interface UserValidator
{
    /**
     * @param user The user to validate
     * @return A validation result might contain error messages preventing the user to be stored
     */
    ValidationResult validateUser(User user);
}
