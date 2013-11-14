package com.mreapps.zapezy.service.service;

import com.mreapps.zapezy.core.validation.ValidationResult;

import java.util.Locale;

/**
 * User methods on the service layer. The service layer can access external services as well as local domain services
 */
public interface UserService
{
    /**
     * Validates input and registeres a new user if input is valid. When a user is registered a confirmation email is sent.
     *
     * @param emailAddress The users email address. This cannot be in use on a different user
     * @param firstName    The users first name
     * @param lastName     The users last name
     * @param password1    The password
     * @param password2    Re-typed password for validation
     * @param locale       The locale used for creating the email address confirmation email
     * @return A validation result with messages. If it contains errors, the user has not been registered.
     */
    ValidationResult registerNewUser(String emailAddress, String firstName, String lastName, String password1, String password2, Locale locale);

    /**
     * @param emailAddress The email address to send token for
     * @param locale       The locale of the email
     * @return A validation result with messages. If it contains errors, no activation token has been sent.
     */
    ValidationResult sendEmailActivationToken(String emailAddress, Locale locale);
}
