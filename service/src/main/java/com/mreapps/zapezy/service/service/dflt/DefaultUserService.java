package com.mreapps.zapezy.service.service.dflt;

import com.mreapps.zapezy.core.validation.DefaultValidationResult;
import com.mreapps.zapezy.core.validation.ValidationMessage;
import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.core.validation.ValidationSeverity;
import com.mreapps.zapezy.domain.entity.User;
import com.mreapps.zapezy.domain.service.DomainUserService;
import com.mreapps.zapezy.service.service.MailService;
import com.mreapps.zapezy.service.service.MessageService;
import com.mreapps.zapezy.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 *
 */
@Service
public class DefaultUserService implements UserService
{
    @Autowired
    private DomainUserService domainUserService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageService messageService;

    @Override
    public ValidationResult registerNewUser(String emailAddress, String firstName, String lastName, String password1, String password2, Locale locale)
    {
        ValidationResult validationResult = domainUserService.registerNewUser(emailAddress, firstName, lastName, password1, password2);
        if (validationResult.isOk())
        {
            validationResult.appendValidationResult(sendEmailActivationToken(emailAddress, locale));
        }
        return validationResult;
    }

    @Override
    public ValidationResult sendEmailActivationToken(String emailAddress, Locale locale)
    {
        ValidationResult validationResult = new DefaultValidationResult();
        User user = domainUserService.findUserDetails(emailAddress);
        if (user == null)
        {
            validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "email_address_x_not_found", emailAddress));
        } else
        {
            if (user.isEmailConfirmed())
            {
                validationResult.addMessage(new ValidationMessage(ValidationSeverity.INFO, "email_address_already_confirmed"));
            } else
            {
                String subject = messageService.getMessage("email_confirmation_subject", locale);
                String message = messageService.getMessage("email_confirmation_message", locale, user.getEmailConfirmationToken());
                mailService.sendMail(emailAddress, subject, message);
            }
        }

        return validationResult;
    }
}
