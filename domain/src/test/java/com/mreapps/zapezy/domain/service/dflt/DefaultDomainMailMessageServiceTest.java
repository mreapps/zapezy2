package com.mreapps.zapezy.domain.service.dflt;

import com.mreapps.zapezy.dao.entity.common.JpaMailMessage;
import com.mreapps.zapezy.dao.repository.JpaMailMessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 *
 */
public class DefaultDomainMailMessageServiceTest
{
    @Mock
    private JpaMailMessageRepository mailMessageRepository;

    private DefaultDomainMailMessageService domainMailMessageService;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        domainMailMessageService = new DefaultDomainMailMessageService();

        ReflectionTestUtils.setField(domainMailMessageService, "mailMessageRepository", mailMessageRepository);
    }

    @Test
    public void logMailMessage()
    {
        Mockito.doAnswer(new Answer<Void>()
        {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable
            {
                JpaMailMessage mailMessage = (JpaMailMessage)invocation.getArguments()[0];
                mailMessage.setId(1L);
                return null;
            }
        }).when(mailMessageRepository).persist(any(JpaMailMessage.class));

        long id = domainMailMessageService.logMailMessage("sender@zapezy.com", "recepient@zapezy.com", "subject", "message");
        assertThat(id).isEqualTo(1);
    }

    @Test
    public void markMessageAsSent()
    {
        JpaMailMessage mailMessage = new JpaMailMessage();
        assertThat(mailMessage.getMessageSent()).isNull();

        when(mailMessageRepository.get(anyLong())).thenReturn(mailMessage);
        domainMailMessageService.markMessageAsSent(1);
        assertThat(mailMessage.getMessageSent()).isNotNull();
    }

    @Test
    public void markMessageAsSentUnknownId()
    {
        boolean caught = false;
        try
        {
            domainMailMessageService.markMessageAsSent(1);
        }
        catch (IllegalArgumentException e)
        {
            caught = true;
            assertThat(e.getMessage()).isEqualTo("Message id 1 not found");
        }
        assertThat(caught).isTrue();
    }

    @Test
    public void logError()
    {
        JpaMailMessage mailMessage = new JpaMailMessage();
        assertThat(mailMessage.getErrorMessage()).isNull();

        when(mailMessageRepository.get(anyLong())).thenReturn(mailMessage);
        domainMailMessageService.logError(1, "error");
        assertThat(mailMessage.getErrorMessage()).isEqualTo("error");
    }

    @Test
    public void logErrorUnknownId()
    {
        boolean caught = false;
        try
        {
            domainMailMessageService.logError(1, "error");
        }
        catch (IllegalArgumentException e)
        {
            caught = true;
            assertThat(e.getMessage()).isEqualTo("Message id 1 not found");
        }
        assertThat(caught).isTrue();
    }
}
