package com.mreapps.zapezy.service.service.dflt;

import com.mreapps.zapezy.core.validation.ValidationResult;
import com.mreapps.zapezy.domain.service.DomainUserService;
import com.mreapps.zapezy.service.service.MailService;
import com.mreapps.zapezy.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ValidationResult registerNewUser(String emailAddress, String firstName, String lastName, String password1, String password2)
    {
        ValidationResult validationResult = domainUserService.registerNewUser(emailAddress, firstName, lastName, password1, password2);
        if(validationResult.isOk())
        {
            // TODO create subject and message
            String subject = "";
            String message = "";
            mailService.sendMail(emailAddress, subject, message);
        }
        return validationResult;
    }
}
