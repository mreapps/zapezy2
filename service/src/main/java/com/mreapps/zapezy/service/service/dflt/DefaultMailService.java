package com.mreapps.zapezy.service.service.dflt;

import com.mreapps.zapezy.domain.service.DomainMailMessageService;
import com.mreapps.zapezy.service.service.MailService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Sends mail using spring mailSender
 */
@Service
public class DefaultMailService implements MailService
{
    @Value("${mail.smtphost}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Autowired
    private DomainMailMessageService domainMailMessageService;

    @Override
    public void sendMail(String recepient, String subject, String message)
    {
        Validate.notNull(recepient, "recepient: null");
        Validate.notNull(subject, "subject: null");
        Validate.notNull(message, "message: null");

        long messageId = domainMailMessageService.logMailMessage(getSender(), recepient, subject, message);
        try
        {
            JavaMailSender mailSender = createMailSender();
            mailSender.send(createMessage(recepient, subject, message, mailSender));
            domainMailMessageService.markMessageAsSent(messageId);
        } catch (Exception e)
        {
            domainMailMessageService.logError(messageId, e.getMessage());
        }
    }

    private String getSender()
    {
        return "do_not_reply@zapezy.com";
    }

    private MimeMessage createMessage(String recepient, String subject, String messageBody, JavaMailSender mailSender) throws MessagingException
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(getSender());
        helper.setSubject(subject);
        helper.setTo(recepient);
        helper.setText(messageBody);

        return mimeMessage;
    }

    private JavaMailSender createMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        return mailSender;
    }
}
