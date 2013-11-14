package com.mreapps.zapezy.service.service.dflt;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Locale;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 *
 */
public class DefaultMessageServiceTest
{
    @Mock
    private MessageSource messageSource;

    private DefaultMessageService defaultMessageService;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        defaultMessageService = new DefaultMessageService();

        ReflectionTestUtils.setField(defaultMessageService, "messageSource", messageSource);
    }

    @Test
    public void getMessage()
    {
        String code = "test";
        String answer = "TranslatedMessage";

        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class))).thenReturn(answer);
        String message = defaultMessageService.getMessage(code, Locale.ENGLISH);
        assertThat(message).isEqualTo(answer);
    }

    @Test
    public void getMessageWithException()
    {
        String code = "test";
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class))).thenThrow(new NoSuchMessageException("dummy"));
        String message = defaultMessageService.getMessage(code, Locale.ENGLISH);
        assertThat(message).isEqualTo("test");
    }
}
