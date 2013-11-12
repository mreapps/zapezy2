package com.mreapps.zapezy.dao.entity.common;

import com.mreapps.zapezy.dao.entity.AbstractJpaBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Log entry for email messages sent from the application
 */
@Entity(name = "mail_message")
public class JpaMailMessage extends AbstractJpaBaseEntity
{
    public static final int MAX_SENDER_LENGTH = 256;
    public static final int MAX_RECEPIENT_LENGTH = 1024;
    public static final int MAX_SUBJECT_LENGTH = 256;
    public static final int MAX_ERROR_MESSAGE_LENGTH = 1024;

    @Column(name = "message_created", nullable = false)
    private Date messageCreated;

    @Column(name = "message_sent")
    private Date messageSent;

    @Column(name = "sender", nullable = false, length = MAX_SENDER_LENGTH)
    private String sender;

    @Column(name = "recepient", nullable = false, length = MAX_RECEPIENT_LENGTH)
    private String recepient;

    @Column(name = "subject", nullable = false, length = MAX_SUBJECT_LENGTH)
    private String subject;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "error_message", nullable = false, length = MAX_ERROR_MESSAGE_LENGTH)
    private String errorMessage;

    public Date getMessageCreated()
    {
        return messageCreated;
    }

    public void setMessageCreated(Date messageCreated)
    {
        this.messageCreated = messageCreated;
    }

    public Date getMessageSent()
    {
        return messageSent;
    }

    public void setMessageSent(Date messageSent)
    {
        this.messageSent = messageSent;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public String getRecepient()
    {
        return recepient;
    }

    public void setRecepient(String recepient)
    {
        this.recepient = recepient;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
