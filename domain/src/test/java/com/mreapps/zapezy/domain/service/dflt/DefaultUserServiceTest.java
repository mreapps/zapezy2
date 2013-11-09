package com.mreapps.zapezy.domain.service.dflt;

import com.mreapps.zapezy.core.validation.DefaultValidationResult;
import com.mreapps.zapezy.core.validation.ValidationMessage;
import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.core.validation.ValidationSeverity;
import com.mreapps.zapezy.dao.entity.JpaUser;
import com.mreapps.zapezy.dao.repository.JpaUserRepository;
import com.mreapps.zapezy.domain.converter.UserConverter;
import com.mreapps.zapezy.domain.entity.User;
import com.mreapps.zapezy.domain.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class DefaultUserServiceTest
{
    @Mock
    private JpaUserRepository userRepository;
    @Mock
    private UserValidator userValidator;
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
        ReflectionTestUtils.setField(defaultUserService, "userConverter", userConverter);
    }

    @Test
    public void registerNewUser()
    {
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";

        when(userValidator.validateUser(any(User.class))).thenReturn(new DefaultValidationResult());
        when(userConverter.convertToDao(any(User.class))).thenReturn(new JpaUser());

        ValidationResult validationResult = defaultUserService.registerNewUser(emailAddress, firstName, lastName);
        assertThat(validationResult.isOk()).isTrue();
    }

    @Test
    public void registerNewUserWithErrors()
    {
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";

        DefaultValidationResult validationResultWithErrors = new DefaultValidationResult();
        validationResultWithErrors.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "dummy error"));
        when(userValidator.validateUser(any(User.class))).thenReturn(validationResultWithErrors);
        when(userConverter.convertToDao(any(User.class))).thenReturn(new JpaUser());

        ValidationResult validationResult = defaultUserService.registerNewUser(emailAddress, firstName, lastName);
        assertThat(validationResult.isOk()).isFalse();
    }
}
