package com.mreapps.zapezy.core.validation;

/**
 * Legal validation severity values
 */
public enum ValidationSeverity
{
    INFO(false),
    WARNING(false),
    ERROR(true),
    CRITICAL(true);

    private final boolean error;

    private ValidationSeverity(boolean error)
    {
        this.error = error;
    }

    public boolean isError()
    {
        return error;
    }
}
