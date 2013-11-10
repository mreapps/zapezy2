package com.mreapps.zapezy.core.validation;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation of {@link ValidationResult}
 */
public class DefaultValidationResult implements ValidationResult
{
    private boolean ok = true;
    private final List<ValidationMessage> messages = new ArrayList<>();

    @Override
    public boolean isOk()
    {
        return ok;
    }

    @Override
    public List<ValidationMessage> getAllMessages()
    {
        return Collections.unmodifiableList(messages);
    }

    @Override
    public List<ValidationMessage> getMessagesBySeverity(ValidationSeverity severity, ValidationSeverity... additionalSeverities)
    {
        Validate.notNull(severity, "severity: null");
        List<ValidationSeverity> severities = new ArrayList<>();
        severities.add(severity);
        if (additionalSeverities.length > 0)
        {
            severities.addAll(Arrays.asList(additionalSeverities));
        }

        ArrayList<ValidationMessage> list = new ArrayList<>();
        for (ValidationMessage message : this.messages)
        {
            if (severities.contains(message.getSeverity()))
            {
                list.add(message);
            }
        }
        return list;
    }

    @Override
    public void addMessage(ValidationMessage message)
    {
        Validate.notNull(message, "message: null");
        this.messages.add(message);
        if (message.getSeverity().isError())
        {
            this.ok = false;
        }
    }

    @Override
    public void appendValidationResult(ValidationResult validationResult)
    {
        if (validationResult != null)
        {
            for (ValidationMessage validationMessage : validationResult.getAllMessages())
            {
                addMessage(validationMessage);
            }
        }
    }
}
