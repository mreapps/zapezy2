package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.core.validation.ValidationResult;

/**
 * User methods on the service layer. The service layer can access external services as well as local domain services
 */
public interface UserService
{
    /**
     * Validates input and registeres a new user if input is valid. When a user is registered a confirmation email is sent.
     * @param emailAddress The users email address. This cannot be in use on a different user
     * @param firstName    The users first name
     * @param lastName     The users last name
     * @param password1    The password
     * @param password2    Re-typed password for validation
     * @return A validation result with messages. If it contains errors, the user has not been registered.
     */
    ValidationResult registerNewUser(String emailAddress, String firstName, String lastName, String password1, String password2);
}
