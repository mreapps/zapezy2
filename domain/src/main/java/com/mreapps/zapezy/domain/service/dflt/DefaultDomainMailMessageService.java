package com.mreapps.zapezy.domain.service.dflt;

import com.mreapps.zapezy.dao.entity.common.JpaMailMessage;
import com.mreapps.zapezy.dao.repository.JpaMailMessageRepository;
import com.mreapps.zapezy.domain.service.DomainMailMessageService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Service for handling log messages
 */
@Service
@Transactional(readOnly = true)
public class DefaultDomainMailMessageService implements DomainMailMessageService
{
    @Autowired
    private JpaMailMessageRepository mailMessageRepository;

    @Override
    @Transactional(readOnly = false)
    public long logMailMessage(String sender, String recepient, String subject, String message)
    {
        Validate.notNull(sender, "sender: null");
        Validate.notNull(recepient, "recepient: null");
        Validate.notNull(subject, "subject: null");
        Validate.notNull(message, "message: null");

        JpaMailMessage jpaMailMessage = new JpaMailMessage();
        jpaMailMessage.setMessageCreated(new Date());
        jpaMailMessage.setSender(sender);
        jpaMailMessage.setRecepient(recepient);
        jpaMailMessage.setSubject(subject);
        jpaMailMessage.setMessage(message);
        mailMessageRepository.persist(jpaMailMessage);

        return jpaMailMessage.getId();
    }

    @Override
    @Transactional(readOnly = false)
    public void markMessageAsSent(long messageId)
    {
        JpaMailMessage jpaMailMessage = mailMessageRepository.get(messageId);
        if (jpaMailMessage == null)
        {
            throw new IllegalArgumentException(String.format("Message id %d not found", messageId));
        }
        jpaMailMessage.setMessageSent(new Date());
        mailMessageRepository.merge(jpaMailMessage);
    }

    @Override
    @Transactional(readOnly = false)
    public void logError(long messageId, String errorMessage)
    {
        JpaMailMessage jpaMailMessage = mailMessageRepository.get(messageId);
        if (jpaMailMessage == null)
        {
            throw new IllegalArgumentException(String.format("Message id %d not found", messageId));
        }
        jpaMailMessage.setErrorMessage(errorMessage);
        mailMessageRepository.merge(jpaMailMessage);
    }
}
