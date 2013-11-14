package com.mreapps.zapezy.service.service;

import java.util.Locale;

/**
 * Gets messages and labels from resource bundles
 */
public interface MessageService
{
    /**
     * @param code          Resource key
     * @param locale        The locale to get the message for
     * @param messageParams Optional params to insert into the message
     * @return The message in the supplied locale. If no matching message is found, code is returned
     */
    String getMessage(String code, Locale locale, Object... messageParams);
}
