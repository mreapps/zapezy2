package com.mreapps.zapezy.service.service.dflt;

import com.mreapps.zapezy.core.validation.DefaultValidationResult;
import com.mreapps.zapezy.core.validation.ValidationMessage;
import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.core.validation.ValidationSeverity;
import com.mreapps.zapezy.domain.service.DomainUserService;
import com.mreapps.zapezy.service.service.MailService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
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

    private DefaultUserService defaultUserService;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        defaultUserService = new DefaultUserService();

        ReflectionTestUtils.setField(defaultUserService, "domainUserService", domainUserService);
        ReflectionTestUtils.setField(defaultUserService, "mailService", mailService);
    }

    @Test
    public void registerNewUser()
    {
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";
        String password = "password";
        when(domainUserService.registerNewUser(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(new DefaultValidationResult());
        ValidationResult validationResult = defaultUserService.registerNewUser(emailAddress, firstName, lastName, password, password);
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
        ValidationResult validationResult = defaultUserService.registerNewUser(emailAddress, firstName, lastName, password, password);
        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().size()).isEqualTo(1);
        assertThat(validationResult.getAllMessages().get(0).getSeverity()).isEqualTo(ValidationSeverity.ERROR);
        assertThat(validationResult.getAllMessages().get(0).getMessage()).isEqualTo("dummy");
        assertThat(validationResult.getAllMessages().get(0).getMessageParams()).isNull();
    }
}
