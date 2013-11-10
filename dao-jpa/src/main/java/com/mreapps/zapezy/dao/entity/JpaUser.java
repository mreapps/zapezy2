package com.mreapps.zapezy.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * A user can log on to the application
 */
@Entity(name = "users")
public class JpaUser extends AbstractJpaBaseEntity
{
    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "encrypted_password")
    private String encryptedPassword;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getEncryptedPassword()
    {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword)
    {
        this.encryptedPassword = encryptedPassword;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Override
    public String toString()
    {
        return String.format(
                "JpaUser[id=%d, emailAddress='%s', firstName='%s', lastName='%s']",
                getId(), emailAddress, firstName, lastName);
    }
}
