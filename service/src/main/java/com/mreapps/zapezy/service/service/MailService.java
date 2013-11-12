package com.mreapps.zapezy.service.service;

/**
 * Interface for sending email from the application
 */
public interface MailService
{
    /**
     * Sends mail to the specified recepient
     *
     * @param recepient The recepients email address
     * @param subject   The email subject
     * @param message   The email message
     */
    void sendMail(String recepient, String subject, String message);
}
