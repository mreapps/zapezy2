package com.mreapps.zapezy.core.validation;

import org.apache.commons.lang.Validate;

import java.io.Serializable;

/**
 * A validation message contains a severity, a message and optional message params
 */
public class ValidationMessage implements Serializable
{
    private final ValidationSeverity severity;
    private final String message;
    private final Object[] messageParams;

    /**
     *
     * @param severity The severity of the message
     * @param message The message
     * @param messageParams Optional message params
     */
    public ValidationMessage(ValidationSeverity severity, String message, Object... messageParams)
    {
        Validate.notNull(severity, "severity: null");
        Validate.notNull(message, "message: null");

        this.severity = severity;
        this.message = message;
        this.messageParams = messageParams.length == 0 ? null : messageParams;
    }

    /**
     * @return Severity
     */
    public ValidationSeverity getSeverity()
    {
        return severity;
    }

    /**
     * @return message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @return message params. Might be null
     */
    public Object[] getMessageParams()
    {
        return messageParams;
    }
}
