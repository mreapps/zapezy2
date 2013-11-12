package com.mreapps.zapezy.service.service.dflt;

import com.mreapps.zapezy.domain.service.DomainMailMessageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Properties;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Tests {@link DefaultMailService}
 */
public class DefaultMailServiceTest
{
    @Mock
    private DomainMailMessageService domainMailMessageService;

    private DefaultMailService defaultMailService;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        defaultMailService = new DefaultMailService();

        ReflectionTestUtils.setField(defaultMailService, "domainMailMessageService", domainMailMessageService);

        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("email.properties"));
        ReflectionTestUtils.setField(defaultMailService, "host", properties.getProperty("host"));
        ReflectionTestUtils.setField(defaultMailService, "port", Integer.parseInt(properties.getProperty("port")));
        ReflectionTestUtils.setField(defaultMailService, "username", properties.getProperty("username"));
        ReflectionTestUtils.setField(defaultMailService, "password", properties.getProperty("password"));
    }

    @Test
    public void sendMail()
    {
        long messageId = 1;
        when(domainMailMessageService.logMailMessage(anyString(), anyString(), anyString(), anyString())).thenReturn(messageId);

        String recepient = "support@zapezy.com";
        String subject = "Unit-test mail";
        String message = "Mail sent from message unit test in zapezy.com";

        defaultMailService.sendMail(recepient, subject, message);
    }

    @Test
    public void sendMailWithException()
    {
        long messageId = 1;
        when(domainMailMessageService.logMailMessage(anyString(), anyString(), anyString(), anyString())).thenReturn(messageId);
        Mockito.doThrow(new RuntimeException()).when(domainMailMessageService).markMessageAsSent(messageId);

        String recepient = "support@zapezy.com";
        String subject = "Unit-test mail";
        String message = "Mail sent from message unit test in zapezy.com";

        defaultMailService.sendMail(recepient, subject, message);
    }
}
