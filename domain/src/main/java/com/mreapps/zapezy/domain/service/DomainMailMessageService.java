package com.mreapps.zapezy.domain.service;

/**
 * Service for handling log messages
 */
public interface DomainMailMessageService
{
    /**
     * @param sender    The from address
     * @param recepient The to address
     * @param subject   The email subject
     * @param message   The email message
     * @return The id of the logged message
     */
    long logMailMessage(String sender, String recepient, String subject, String message);

    /**
     * @param messageId The id of the message to mark as sent
     */
    void markMessageAsSent(long messageId);

    /**
     * @param messageId    The id of the message to log error on
     * @param errorMessage The error message to log
     */
    void logError(long messageId, String errorMessage);
}
