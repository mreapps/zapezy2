package com.mreapps.zapezy.domain.entity;

import java.io.Serializable;

/**
 * A user registered in zapezy
 */
public class User extends AbstractBaseEntity implements Serializable
{
    private static final long serialVersionUID = -4030934278952958273L;

    private String emailAddress;
    private String firstName;
    private String lastName;
    private String role;
    private String emailConfirmationToken;
    private boolean emailConfirmed;

    /**
     * Default constructor
     */
    public User()
    {
    }

    /**
     * @param id A unique id for the user. This id is final and cannot be changed
     */
    public User(long id)
    {
        super(id);
    }

    /**
     * @return The users email address. This is unique and is used to identify the user
     */
    public String getEmailAddress()
    {
        return emailAddress;
    }

    /**
     * @param emailAddress The users email address. This is unique and is used to identify the user
     */
    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    /**
     * @return The users first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName The users first name
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @return The users last name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName The users last name
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getEmailConfirmationToken()
    {
        return emailConfirmationToken;
    }

    public void setEmailConfirmationToken(String emailConfirmationToken)
    {
        this.emailConfirmationToken = emailConfirmationToken;
    }

    public boolean isEmailConfirmed()
    {
        return emailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed)
    {
        this.emailConfirmed = emailConfirmed;
    }
}
