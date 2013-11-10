package com.mreapps.zapezy.domain.service;

import com.mreapps.zapezy.core.validation.ValidationResult;

/**
 * User services
 */
public interface UserService
{
    /**
     * @param emailAddress The users email address. This cannot be in use on a different user
     * @param firstName    The users first name
     * @param lastName     The users last name
     * @param password1    The password
     * @param password2    Re-typed password for validation
     * @return A validation result with messages. If it contains errors, the user has not been registered.
     */
    ValidationResult registerNewUser(String emailAddress, String firstName, String lastName, String password1, String password2);

    /**
     * @param emailAddress The users email address
     * @param password     The password to validate
     * @return true is the email address and password is a match
     */
    boolean checkPassword(String emailAddress, String password);
}
