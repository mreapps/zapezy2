package com.mreapps.zapezy.dao.entity.user;

import com.mreapps.zapezy.dao.entity.AbstractJpaBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * A user can log on to the application
 */
@Entity(name = "users")
public class JpaUser extends AbstractJpaBaseEntity
{
    public static final int MAX_EMAIL_LENGTH = 256;
    public static final int MAX_FIRST_NAME_LENGTH = 40;
    public static final int MAX_LAST_NAME_LENGTH = 50;
    public static final int MAX_TOKEN_LENGTH = 256;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 50;

    @Column(name = "email_address", nullable = false, length = MAX_EMAIL_LENGTH)
    private String emailAddress;

    @Column(name = "encrypted_password", nullable = false, length = MAX_TOKEN_LENGTH)
    private String encryptedPassword;

    @Column(name = "first_name", nullable = false, length = MAX_FIRST_NAME_LENGTH)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = MAX_LAST_NAME_LENGTH)
    private String lastName;

    @Column(name = "email_confirmation_token", nullable = true, length = MAX_TOKEN_LENGTH, unique = true)
    private String emailConfirmationToken;

    @Column(name = "email_confirmed", nullable = false)
    private boolean emailConfirmed;

    @Column(name = "user_registered_date", nullable = false)
    private Date userRegisteredDate;

    @Column(name = "email_confirmed_date", nullable = true)
    private Date emailConfirmedDate;

    @ManyToOne
    @JoinColumn(name = "role_uid", nullable = false)
    private JpaRole role;

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

    public JpaRole getRole()
    {
        return role;
    }

    public void setRole(JpaRole role)
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

    public Date getUserRegisteredDate()
    {
        return userRegisteredDate;
    }

    public void setUserRegisteredDate(Date userRegisteredDate)
    {
        this.userRegisteredDate = userRegisteredDate;
    }

    public Date getEmailConfirmedDate()
    {
        return emailConfirmedDate;
    }

    public void setEmailConfirmedDate(Date emailConfirmedDate)
    {
        this.emailConfirmedDate = emailConfirmedDate;
    }

    @Override
    public String toString()
    {
        return String.format(
                "JpaUser[id=%d, emailAddress='%s', firstName='%s', lastName='%s', role='%s']",
                getId(), emailAddress, firstName, lastName, role.getCode());
    }
}
