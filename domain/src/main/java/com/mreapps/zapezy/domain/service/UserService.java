package com.mreapps.zapezy.domain.service;

import com.mreapps.zapezy.core.validation.ValidationResult;

/**
 * User services
 */
public interface UserService
{
    /**
     *
     * @param emailAddress The users email address. This cannot be in use on a different user
     * @param firstName The users first name
     * @param lastName The users last name
     * @return A validation result with messages. If it contains errors, the user has not been registered.
     */
    ValidationResult registerNewUser(String emailAddress, String firstName, String lastName);
}
