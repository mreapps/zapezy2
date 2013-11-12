package com.mreapps.zapezy.domain.validator.dflt;

import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.dao.entity.user.JpaUser;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests {@link DefaultPasswordValidator}
 */
public class DefaultPasswordValidatorTest
{
    @Test
    public void testNullPassword()
    {
        DefaultPasswordValidator passwordValidator = new DefaultPasswordValidator();
        ValidationResult validationResult = passwordValidator.validatePasswords(null, null);
        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("password_must_contain_at_least_x_chars");
        assertThat(validationResult.getAllMessages().get(0).getMessageParams().length).isEqualTo(1);
        assertThat(validationResult.getAllMessages().get(0).getMessageParams()[0]).isEqualTo(JpaUser.MIN_PASSWORD_LENGTH);
    }

    @Test
    public void testBlankPassword()
    {
        DefaultPasswordValidator passwordValidator = new DefaultPasswordValidator();
        ValidationResult validationResult = passwordValidator.validatePasswords("", "");

        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("password_must_contain_at_least_x_chars");
        assertThat(validationResult.getAllMessages().get(0).getMessageParams().length).isEqualTo(1);
        assertThat(validationResult.getAllMessages().get(0).getMessageParams()[0]).isEqualTo(JpaUser.MIN_PASSWORD_LENGTH);
    }

    @Test
    public void testShortPassword()
    {
        DefaultPasswordValidator passwordValidator = new DefaultPasswordValidator();
        ValidationResult validationResult = passwordValidator.validatePasswords("123", "123");

        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("password_must_contain_at_least_x_chars");
        assertThat(validationResult.getAllMessages().get(0).getMessageParams().length).isEqualTo(1);
        assertThat(validationResult.getAllMessages().get(0).getMessageParams()[0]).isEqualTo(JpaUser.MIN_PASSWORD_LENGTH);
    }

    @Test
    public void testLongPassword()
    {
        DefaultPasswordValidator passwordValidator = new DefaultPasswordValidator();
        ValidationResult validationResult = passwordValidator.validatePasswords(
                "123456789012345678901234567890123456789012345678901",
                "123456789012345678901234567890123456789012345678901"
        );

        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("password_cannot_contain_more_than_x_chars");
        assertThat(validationResult.getAllMessages().get(0).getMessageParams().length).isEqualTo(1);
        assertThat(validationResult.getAllMessages().get(0).getMessageParams()[0]).isEqualTo(JpaUser.MAX_PASSWORD_LENGTH);
    }

    @Test
    public void testPassword2Mismatch()
    {
        DefaultPasswordValidator passwordValidator = new DefaultPasswordValidator();
        ValidationResult validationResult = passwordValidator.validatePasswords("123456", "654321");

        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("passwords_do_not_match");
        assertThat(validationResult.getAllMessages().get(0).getMessageParams()).isNull();
    }

    @Test
    public void testValidPassword()
    {
        DefaultPasswordValidator passwordValidator = new DefaultPasswordValidator();
        ValidationResult validationResult = passwordValidator.validatePasswords("123456", "123456");

        assertThat(validationResult.isOk()).isTrue();
        assertThat(validationResult.getAllMessages().size()).isEqualTo(0);
    }
}
