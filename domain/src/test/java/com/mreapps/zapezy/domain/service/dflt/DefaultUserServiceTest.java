package com.mreapps.zapezy.domain.service.dflt;

import com.mreapps.zapezy.core.validation.DefaultValidationResult;
import com.mreapps.zapezy.core.validation.ValidationMessage;
import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.core.validation.ValidationSeverity;
import com.mreapps.zapezy.dao.entity.JpaUser;
import com.mreapps.zapezy.dao.repository.JpaUserRepository;
import com.mreapps.zapezy.domain.converter.UserConverter;
import com.mreapps.zapezy.domain.entity.User;
import com.mreapps.zapezy.domain.validator.PasswordValidator;
import com.mreapps.zapezy.domain.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class DefaultUserServiceTest
{
    @Mock
    private JpaUserRepository userRepository;
    @Mock
    private UserValidator userValidator;
    @Mock
    private PasswordValidator passwordValidator;
    @Mock
    private UserConverter userConverter;

    private DefaultUserService defaultUserService;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        defaultUserService = new DefaultUserService();

        ReflectionTestUtils.setField(defaultUserService, "userRepository", userRepository);
        ReflectionTestUtils.setField(defaultUserService, "userValidator", userValidator);
        ReflectionTestUtils.setField(defaultUserService, "passwordValidator", passwordValidator);
        ReflectionTestUtils.setField(defaultUserService, "userConverter", userConverter);
    }

    @Test
    public void registerNewUser()
    {
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";
        String password = "password";

        when(userValidator.validateUser(any(User.class))).thenReturn(new DefaultValidationResult());
        when(passwordValidator.validatePasswords(any(String.class), any(String.class))).thenReturn(new DefaultValidationResult());
        when(userConverter.convertToDao(any(User.class))).thenReturn(new JpaUser());

        ValidationResult validationResult = defaultUserService.registerNewUser(emailAddress, firstName, lastName, password, password);
        assertThat(validationResult.isOk()).isTrue();
    }

    @Test
    public void registerNewUserWithErrors()
    {
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";
        String password = "password";

        DefaultValidationResult validationResultWithErrors = new DefaultValidationResult();
        validationResultWithErrors.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "dummy error"));
        when(userValidator.validateUser(any(User.class))).thenReturn(validationResultWithErrors);
        when(passwordValidator.validatePasswords(any(String.class), any(String.class))).thenReturn(new DefaultValidationResult());
        when(userConverter.convertToDao(any(User.class))).thenReturn(new JpaUser());

        ValidationResult validationResult = defaultUserService.registerNewUser(emailAddress, firstName, lastName, password, password);
        assertThat(validationResult.isOk()).isFalse();
    }

    @Test
    public void checkPasswordValid()
    {
        String emailAddress = "user@zapezy.com";
        String password = "password";

        JpaUser jpaUser = new JpaUser();
        jpaUser.setEncryptedPassword("uqqcJpViNetIZPfcUZ+7Ys8IxQoxNvXNvkb58uu+1RDoQZ+ufnyMaQ/LF6nKVQD8");
        when(userRepository.findByEmailAddress(eq(emailAddress))).thenReturn(jpaUser);

        boolean validPassword = defaultUserService.checkPassword(emailAddress, password);
        assertThat(validPassword).isTrue();
    }

    @Test
    public void checkPasswordInvalid()
    {
        String emailAddress = "user@zapezy.com";
        String password = "wrongPassword";

        JpaUser jpaUser = new JpaUser();
        jpaUser.setEncryptedPassword("uqqcJpViNetIZPfcUZ+7Ys8IxQoxNvXNvkb58uu+1RDoQZ+ufnyMaQ/LF6nKVQD8");
        when(userRepository.findByEmailAddress(eq(emailAddress))).thenReturn(jpaUser);

        boolean validPassword = defaultUserService.checkPassword(emailAddress, password);
        assertThat(validPassword).isFalse();
    }

    @Test
    public void checkPasswordNullEmail()
    {
        String emailAddress = null;
        String password = "password";

        boolean validPassword = defaultUserService.checkPassword(emailAddress, password);
        assertThat(validPassword).isFalse();
    }

    @Test
    public void changePasswordWrongOldPassword()
    {
        String emailAddress = "user@zapezy.com";
        String oldPassword = "oldPassword";
        String newPassword1 = "newPassword";
        String newPassword2 = "newPassword";

        JpaUser jpaUser = new JpaUser();
        jpaUser.setEncryptedPassword("uqqcJpViNetIZPfcUZ+7Ys8IxQoxNvXNvkb58uu+1RDoQZ+ufnyMaQ/LF6nKVQD8");
        when(userRepository.findByEmailAddress(eq(emailAddress))).thenReturn(jpaUser);

        ValidationResult validationResult = defaultUserService.changePassword(emailAddress, oldPassword, newPassword1, newPassword2);
        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("old_password_is_wrong");
    }

    @Test
    public void changePasswordNewPasswordError()
    {
        String emailAddress = "user@zapezy.com";
        String oldPassword = "password";
        String newPassword1 = "newPassword";
        String newPassword2 = "newPassword2";

        JpaUser jpaUser = new JpaUser();
        jpaUser.setEncryptedPassword("uqqcJpViNetIZPfcUZ+7Ys8IxQoxNvXNvkb58uu+1RDoQZ+ufnyMaQ/LF6nKVQD8");
        when(userRepository.findByEmailAddress(eq(emailAddress))).thenReturn(jpaUser);

        ValidationResult validationResult = new DefaultValidationResult();
        validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "dummy"));
        when(passwordValidator.validatePasswords(anyString(), anyString())).thenReturn(validationResult);

        validationResult = defaultUserService.changePassword(emailAddress, oldPassword, newPassword1, newPassword2);
        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("dummy");
    }

    @Test
    public void changePasswordSuccess()
    {
        String emailAddress = "user@zapezy.com";
        String oldPassword = "password";
        String newPassword1 = "newPassword";
        String newPassword2 = "newPassword2";

        JpaUser jpaUser = new JpaUser();
        jpaUser.setEncryptedPassword("uqqcJpViNetIZPfcUZ+7Ys8IxQoxNvXNvkb58uu+1RDoQZ+ufnyMaQ/LF6nKVQD8");
        when(userRepository.findByEmailAddress(eq(emailAddress))).thenReturn(jpaUser);

        when(passwordValidator.validatePasswords(anyString(), anyString())).thenReturn(new DefaultValidationResult());

        ValidationResult validationResult = defaultUserService.changePassword(emailAddress, oldPassword, newPassword1, newPassword2);
        assertThat(validationResult.isOk()).isTrue();
    }
}
