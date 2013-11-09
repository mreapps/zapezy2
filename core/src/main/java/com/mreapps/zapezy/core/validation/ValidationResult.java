package com.mreapps.zapezy.core.validation;

import java.util.List;

/**
 * A validation result contains all types of messages
 */
public interface ValidationResult
{
    /**
     *
     * @return true if none of the messages has severity ERROR or CRITICAL
     */
    boolean isOk();

    /**
     *
     * @return All validatin messages
     */
    List<ValidationMessage> getAllMessages();

    /**
     *
     * @param severity A severity to filter the returned message(s)
     * @param additionalSeverities Optional additional severities to filter the returned message(s)
     * @return The messages with the supplied severities
     */
    List<ValidationMessage> getMessagesBySeverity(ValidationSeverity severity, ValidationSeverity... additionalSeverities);

    /**
     * Adds a validation message to the validation result
     * @param message The validation message to add
     */
    void addMessage(ValidationMessage message);
}
