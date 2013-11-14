package com.mreapps.zapezy.domain.service.dflt;

import com.mreapps.zapezy.core.validation.DefaultValidationResult;
import com.mreapps.zapezy.core.validation.ValidationMessage;
import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.core.validation.ValidationSeverity;
import com.mreapps.zapezy.dao.entity.user.JpaRole;
import com.mreapps.zapezy.dao.entity.user.JpaUser;
import com.mreapps.zapezy.dao.repository.JpaRoleRepository;
import com.mreapps.zapezy.dao.repository.JpaUserRepository;
import com.mreapps.zapezy.domain.converter.UserConverter;
import com.mreapps.zapezy.domain.entity.User;
import com.mreapps.zapezy.domain.service.DomainUserService;
import com.mreapps.zapezy.domain.validator.PasswordValidator;
import com.mreapps.zapezy.domain.validator.UserValidator;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *
 */
@Service
@Transactional(readOnly = true)
public class DefaultDomainUserService implements DomainUserService
{
    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private JpaRoleRepository roleRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private UserConverter userConverter;

    @Override
    @Transactional(readOnly = false)
    public ValidationResult registerNewUser(String emailAddress, String firstName, String lastName, String password1, String password2)
    {
        Validate.notNull(emailAddress);

        final ValidationResult validationResult;

        User user = new User();
        user.setEmailAddress(emailAddress.toLowerCase());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        // new users should be put into role user by default
        user.setRole("user");
        user.setEmailConfirmationToken(RandomStringUtils.randomAlphanumeric(JpaUser.MAX_TOKEN_LENGTH));
        user.setEmailConfirmed(false);

        validationResult = userValidator.validateUser(user);
        validationResult.appendValidationResult(passwordValidator.validatePasswords(password1, password2));
        if (validationResult.isOk())
        {
            JpaUser jpaUser = userConverter.convertToDao(user);
            jpaUser.setUserRegisteredDate(new Date());
            setEncryptedPassword(jpaUser, password1);
            userRepository.persist(jpaUser);
        }
        return validationResult;
    }

    @Override
    public boolean checkPassword(String emailAddress, String password)
    {
        if (StringUtils.isNotBlank(emailAddress))
        {
            JpaUser jpaUser = userRepository.findByEmailAddress(emailAddress);
            return new StrongPasswordEncryptor().checkPassword(password, jpaUser.getEncryptedPassword());
        }
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public ValidationResult changePassword(String emailAddress, String oldPassword, String newPassword1, String newPassword2)
    {
        ValidationResult validationResult = new DefaultValidationResult();

        boolean oldPasswordValid = checkPassword(emailAddress, oldPassword);
        if (!oldPasswordValid)
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "old_password_is_wrong"));
        } else
        {
            validationResult.appendValidationResult(passwordValidator.validatePasswords(newPassword1, newPassword2));
            if (validationResult.isOk())
            {
                JpaUser jpaUser = userRepository.findByEmailAddress(emailAddress);
                setEncryptedPassword(jpaUser, newPassword1);
                userRepository.merge(jpaUser);
            }
        }

        return validationResult;
    }

    @Override
    @Transactional(readOnly = false)
    public ValidationResult setRole(String emailAddress, String role)
    {
        ValidationResult validationResult = new DefaultValidationResult();

        final JpaUser jpaUser = userRepository.findByEmailAddress(emailAddress);
        if (jpaUser == null)
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "unknown_email_address_x", emailAddress));
        } else
        {
            JpaRole jpaRole = roleRepository.findByCode(role);
            if (jpaRole == null)
            {
                validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "unknown_role_x", role));
            } else
            {
                jpaUser.setRole(jpaRole);
                userRepository.merge(jpaUser);
            }
        }

        return validationResult;
    }

    @Override
    @Transactional(readOnly = false)
    public ValidationResult confirmEmailAddress(String emailConfirmationToken)
    {
        ValidationResult validationResult = new DefaultValidationResult();

        if (StringUtils.isBlank(emailConfirmationToken))
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "missing_confirmation_token"));
        } else
        {
            JpaUser jpaUser = userRepository.findByEmailConfirmationToken(emailConfirmationToken);
            if (jpaUser == null)
            {
                validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "invalid_confirmation_token"));
            } else if (jpaUser.isEmailConfirmed())
            {
                validationResult.addMessage(new ValidationMessage(ValidationSeverity.INFO, "email_has_already_been_confirmed"));
            } else
            {
                jpaUser.setEmailConfirmed(true);
                jpaUser.setEmailConfirmedDate(new Date());
                userRepository.merge(jpaUser);
            }
        }

        return validationResult;
    }

    @Override
    public User findUserDetails(String emailAddress)
    {
        Validate.notNull(emailAddress, "emailAddress: null");

        JpaUser jpaUser = userRepository.findByEmailAddress(emailAddress);
        return userConverter.convertToDomain(jpaUser);
    }

    private void setEncryptedPassword(JpaUser jpaUser, String password)
    {
        jpaUser.setEncryptedPassword(new StrongPasswordEncryptor().encryptPassword(password.trim()));
    }
}
