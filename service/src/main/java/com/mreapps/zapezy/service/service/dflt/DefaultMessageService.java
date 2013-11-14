package com.mreapps.zapezy.service.service.dflt;

import com.mreapps.zapezy.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Uses spring MessageSource to get messages
 */
@Service
public class DefaultMessageService implements MessageService
{
    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String code, Locale locale, Object... messageParams)
    {
        try
        {
            return messageSource.getMessage(code, messageParams, locale);
        } catch (NoSuchMessageException e)
        {
            return code;
        }
    }
}
