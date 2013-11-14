package com.mreapps.zapezy.service.service.dflt;

import com.mreapps.zapezy.core.validation.DefaultValidationResult;
import com.mreapps.zapezy.core.validation.ValidationMessage;
import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.core.validation.ValidationSeverity;
import com.mreapps.zapezy.domain.entity.User;
import com.mreapps.zapezy.domain.service.DomainUserService;
import com.mreapps.zapezy.service.service.MailService;
import com.mreapps.zapezy.service.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Locale;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

/**
 * Tests {@link DefaultUserService}
 */
public class DefaultUserServiceTest
{
    @Mock
    private DomainUserService domainUserService;
    @Mock
    private MailService mailService;
    @Mock
    private MessageService messageService;

    private DefaultUserService defaultUserService;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        defaultUserService = new DefaultUserService();

        ReflectionTestUtils.setField(defaultUserService, "domainUserService", domainUserService);
        ReflectionTestUtils.setField(defaultUserService, "mailService", mailService);
        ReflectionTestUtils.setField(defaultUserService, "messageService", messageService);
    }

    @Test
    public void registerNewUser()
    {
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";
        String password = "password";

        User user = new User();
        user.setEmailAddress(emailAddress);
        when(domainUserService.registerNewUser(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(new DefaultValidationResult());
        when(domainUserService.findUserDetails(anyString())).thenReturn(user);
        ValidationResult validationResult = defaultUserService.registerNewUser(emailAddress, firstName, lastName, password, password, Locale.ENGLISH);
        assertThat(validationResult.isOk()).isTrue();
        assertThat(validationResult.getAllMessages()).isEmpty();
    }

    @Test
    public void registerNewUserValidationError()
    {
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";
        String password = "password";
        ValidationResult validationResultWithErrors = new DefaultValidationResult();
        validationResultWithErrors.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "dummy"));
        when(domainUserService.registerNewUser(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(validationResultWithErrors);
        ValidationResult validationResult = defaultUserService.registerNewUser(emailAddress, firstName, lastName, password, password, Locale.ENGLISH);
        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().size()).isEqualTo(1);
        assertThat(validationResult.getAllMessages().get(0).getSeverity()).isEqualTo(ValidationSeverity.ERROR);
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("dummy");
        assertThat(validationResult.getAllMessages().get(0).getMessageParams()).isNull();
    }

    @Test
    public void sendEmailActivationTokenUnknownEmailAddress()
    {
        String emailAddress = "user@zapezy.com";
        ValidationResult validationResult = defaultUserService.sendEmailActivationToken(emailAddress, Locale.ENGLISH);
        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().size()).isEqualTo(1);
        assertThat(validationResult.getAllMessages().get(0).getSeverity()).isEqualTo(ValidationSeverity.ERROR);
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("email_address_x_not_found");
        assertThat(validationResult.getAllMessages().get(0).getMessageParams().length).isEqualTo(1);
        assertThat(validationResult.getAllMessages().get(0).getMessageParams()[0]).isEqualTo(emailAddress);
    }

    @Test
    public void sendEmailActivationTokenConfirmedEmailAddress()
    {
        String emailAddress = "user@zapezy.com";

        User user = new User();
        user.setEmailAddress(emailAddress);
        user.setEmailConfirmed(true);
        when(domainUserService.findUserDetails(eq(emailAddress))).thenReturn(user);

        ValidationResult validationResult = defaultUserService.sendEmailActivationToken(emailAddress, Locale.ENGLISH);
        assertThat(validationResult.isOk()).isTrue();
        assertThat(validationResult.getAllMessages().size()).isEqualTo(1);
        assertThat(validationResult.getAllMessages().get(0).getSeverity()).isEqualTo(ValidationSeverity.INFO);
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("email_address_already_confirmed");
        assertThat(validationResult.getAllMessages().get(0).getMessageParams()).isNull();
    }

    @Test
    public void sendEmailActivationToken()
    {
        String emailAddress = "user@zapezy.com";

        User user = new User();
        user.setEmailAddress(emailAddress);
        when(domainUserService.findUserDetails(eq(emailAddress))).thenReturn(user);

        ValidationResult validationResult = defaultUserService.sendEmailActivationToken(emailAddress, Locale.ENGLISH);
        assertThat(validationResult.isOk()).isTrue();
        assertThat(validationResult.getAllMessages().size()).isEqualTo(0);
    }
}
