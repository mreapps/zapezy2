package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.common.JpaMailMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests {@link JpaMailMessageRepository}
 */
@ContextConfiguration("/spring/DaoTestContext.xml")
public class JpaMailMessageRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests
{
    @Autowired
    private JpaMailMessageRepository mailMessageRepository;

    @Test
    public void persistMailMessage()
    {
        Date messageCreated = new Date();
        Date messageSent = new Date();
        String sender = "sender";
        String recepient = "recepient";
        String subject = "subject";
        String message = "message";
        String errorMessage = "errorMessage";

        JpaMailMessage mailMessage = new JpaMailMessage();

        mailMessage.setMessageCreated(messageCreated);
        mailMessage.setMessageSent(messageSent);
        mailMessage.setSender(sender);
        mailMessage.setRecepient(recepient);
        mailMessage.setSubject(subject);
        mailMessage.setMessage(message);
        mailMessage.setErrorMessage(errorMessage);

        mailMessageRepository.persist(mailMessage);

        assertThat(mailMessage.getId()).isNotNull();

        long id = mailMessage.getId();
        mailMessage = mailMessageRepository.get(id);
        assertThat(mailMessage).isNotNull();
        assertThat(mailMessage.getId()).isEqualTo(id);
        assertThat(mailMessage.getMessageCreated()).isEqualTo(messageCreated);
        assertThat(mailMessage.getMessageSent()).isEqualTo(messageSent);
        assertThat(mailMessage.getSender()).isEqualTo(sender);
        assertThat(mailMessage.getRecepient()).isEqualTo(recepient);
        assertThat(mailMessage.getSubject()).isEqualTo(subject);
        assertThat(mailMessage.getMessage()).isEqualTo(message);
        assertThat(mailMessage.getErrorMessage()).isEqualTo(errorMessage);
    }
}
