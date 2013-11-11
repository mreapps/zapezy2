package com.mreapps.zapezy.domain.validator.dflt;

import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.dao.entity.user.JpaUser;
import com.mreapps.zapezy.dao.repository.JpaUserRepository;
import com.mreapps.zapezy.domain.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests {@link DefaultUserValidator}
 */
public class DefaultUserValidatorTest
{
    @Mock
    private JpaUserRepository userRepository;

    private DefaultUserValidator userValidator;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        userValidator = new DefaultUserValidator();

        ReflectionTestUtils.setField(userValidator, "userRepository", userRepository);
    }

    @Test
    public void validateValidNewUser()
    {
        User user = new User();
        user.setEmailAddress("user@zapezy.com");
        user.setFirstName("Ryan");
        user.setLastName("Giggs");

        ValidationResult validationResult = userValidator.validateUser(user);
        assertThat(validationResult.isOk()).isTrue();
    }

    @Test
    public void validateNewUserWithInvalidEmailAddress()
    {
        User user = new User();
        user.setEmailAddress("user.zapezy.com");
        user.setFirstName("Ryan");
        user.setLastName("Giggs");

        ValidationResult validationResult = userValidator.validateUser(user);
        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("email_address_is_not_valid");
    }

    @Test
    public void validateValidExistingUser()
    {
        User user = new User(1);
        user.setEmailAddress("user@zapezy.com");
        user.setFirstName("Ryan");
        user.setLastName("Giggs");

        JpaUser existingUser = new JpaUser();
        existingUser.setId(1L);
        when(userRepository.findByEmailAddress(eq(user.getEmailAddress()))).thenReturn(existingUser);

        ValidationResult validationResult = userValidator.validateUser(user);
        assertThat(validationResult.isOk()).isTrue();
    }

    @Test
    public void validateValidUserEmailInUse()
    {
        User user = new User();
        user.setEmailAddress("user@zapezy.com");
        user.setFirstName("Ryan");
        user.setLastName("Giggs");

        JpaUser existingUser = new JpaUser();
        ReflectionTestUtils.setField(existingUser, "id", 1L);
        when(userRepository.findByEmailAddress(eq(user.getEmailAddress()))).thenReturn(existingUser);

        ValidationResult validationResult = userValidator.validateUser(user);
        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("email_address_is_already_in_use");
    }

    @Test
    public void validateValidUserMissingEmail()
    {
        User user = new User();
        user.setFirstName("Ryan");
        user.setLastName("Giggs");

        ValidationResult validationResult = userValidator.validateUser(user);
        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("email_address_must_be_set");
    }

    @Test
    public void validateValidUserMissingFirstName()
    {
        User user = new User();
        user.setEmailAddress("user@zapezy.com");
        user.setLastName("Giggs");

        ValidationResult validationResult = userValidator.validateUser(user);
        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("first_name_must_be_set");
    }

    @Test
    public void validateValidUserMissingLastName()
    {
        User user = new User();
        user.setEmailAddress("user@zapezy.com");
        user.setFirstName("Ryan");

        ValidationResult validationResult = userValidator.validateUser(user);
        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("last_name_must_be_set");
    }
}
