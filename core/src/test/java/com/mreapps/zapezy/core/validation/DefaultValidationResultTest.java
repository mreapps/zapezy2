package com.mreapps.zapezy.core.validation;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests {@link DefaultValidationResult}
 */
public class DefaultValidationResultTest
{
    @Test
    public void getMessages()
    {
        DefaultValidationResult validationResult = new DefaultValidationResult();
        validationResult.addMessage(new ValidationMessage(ValidationSeverity.CRITICAL, "critical1"));
        validationResult.addMessage(new ValidationMessage(ValidationSeverity.CRITICAL, "critical2"));
        validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "error1"));
        validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "error2"));
        validationResult.addMessage(new ValidationMessage(ValidationSeverity.ERROR, "error3"));
        validationResult.addMessage(new ValidationMessage(ValidationSeverity.WARNING, "warning1"));
        validationResult.addMessage(new ValidationMessage(ValidationSeverity.INFO, "info1"));
        validationResult.addMessage(new ValidationMessage(ValidationSeverity.INFO, "info2", "param1", "param2"));

        assertThat(validationResult.isOk()).isFalse();
        assertThat(validationResult.getAllMessages().size()).isEqualTo(8);
        assertThat(validationResult.getMessagesBySeverity(ValidationSeverity.CRITICAL).size()).isEqualTo(2);
        assertThat(validationResult.getMessagesBySeverity(ValidationSeverity.ERROR).size()).isEqualTo(3);
        assertThat(validationResult.getMessagesBySeverity(ValidationSeverity.CRITICAL, ValidationSeverity.ERROR).size()).isEqualTo(5);
        assertThat(validationResult.getMessagesBySeverity(ValidationSeverity.CRITICAL, ValidationSeverity.ERROR, ValidationSeverity.WARNING).size()).isEqualTo(6);
        assertThat(validationResult.getMessagesBySeverity(ValidationSeverity.WARNING).size()).isEqualTo(1);
        assertThat(validationResult.getMessagesBySeverity(ValidationSeverity.INFO).size()).isEqualTo(2);
    }

    @Test
    public void getMessageProperties()
    {
        DefaultValidationResult validationResult = new DefaultValidationResult();

        ValidationMessage info1 = new ValidationMessage(ValidationSeverity.INFO, "info1");
        assertThat(info1.getMessage()).isEqualTo("info1");
        assertThat(info1.getSeverity()).isEqualTo(ValidationSeverity.INFO);
        assertThat(info1.getMessageParams()).isNull();

        ValidationMessage warning1 = new ValidationMessage(ValidationSeverity.WARNING, "warning1", "param1", "param2");
        assertThat(warning1.getMessage()).isEqualTo("warning1");
        assertThat(warning1.getSeverity()).isEqualTo(ValidationSeverity.WARNING);
        assertThat(warning1.getMessageParams().length).isEqualTo(2);
        assertThat(warning1.getMessageParams()[0]).isEqualTo("param1");
        assertThat(warning1.getMessageParams()[1]).isEqualTo("param2");

        validationResult.addMessage(info1);
        validationResult.addMessage(warning1);

        assertThat(validationResult.isOk()).isTrue();
        assertThat(validationResult.getAllMessages().size()).isEqualTo(2);
    }

    @Test
    public void appendValidationResult()
    {
        DefaultValidationResult validationResult1 = new DefaultValidationResult();
        validationResult1.addMessage(new ValidationMessage(ValidationSeverity.CRITICAL, "critical1"));

        DefaultValidationResult validationResult2 = new DefaultValidationResult();
        validationResult2.addMessage(new ValidationMessage(ValidationSeverity.CRITICAL, "critical2"));

        validationResult1.appendValidationResult(validationResult2);
        assertThat(validationResult1.getAllMessages().size()).isEqualTo(2);
    }

    @Test
    public void appendNullValidationResult()
    {
        DefaultValidationResult validationResult1 = new DefaultValidationResult();
        validationResult1.addMessage(new ValidationMessage(ValidationSeverity.CRITICAL, "critical1"));
        DefaultValidationResult validationResult2 = null;

        validationResult1.appendValidationResult(validationResult2);
        assertThat(validationResult1.getAllMessages().size()).isEqualTo(1);
    }
}
