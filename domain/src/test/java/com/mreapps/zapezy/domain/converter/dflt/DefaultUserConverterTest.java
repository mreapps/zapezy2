package com.mreapps.zapezy.domain.converter.dflt;

import com.mreapps.zapezy.dao.entity.JpaUser;
import com.mreapps.zapezy.domain.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests {@link DefaultUserConverter}
 */
public class DefaultUserConverterTest
{
    private DefaultUserConverter userConverter;
    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        userConverter = new DefaultUserConverter();
    }

    @Test
    public void convertToJpaUser()
    {
        long id = 1;
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";

        User user = new User(id);
        user.setEmailAddress(emailAddress);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        JpaUser jpaUser = userConverter.convertToDao(user);
        assertThat(jpaUser).isNotNull();
        assertThat(jpaUser.getId()).isEqualTo(id);
        assertThat(jpaUser.getEmailAddress()).isEqualTo(emailAddress);
        assertThat(jpaUser.getFirstName()).isEqualTo(firstName);
        assertThat(jpaUser.getLastName()).isEqualTo(lastName);

        String toString = String.format(
            "JpaUser[id=%d, emailAddress='%s', firstName='%s', lastName='%s']",
            id, emailAddress, firstName, lastName);
        assertThat(jpaUser.toString()).isEqualTo(toString);
    }

    @Test
    public void convertNullToJpaUser()
    {
        JpaUser jpaUser = userConverter.convertToDao(null);
        assertThat(jpaUser).isNull();
    }

    @Test
    public void convertNewUserToDomainUser()
    {
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";

        JpaUser jpaUser = new JpaUser();
        jpaUser.setEmailAddress(emailAddress);
        jpaUser.setFirstName(firstName);
        jpaUser.setLastName(lastName);

        User user = userConverter.convertToDomain(jpaUser);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getEmailAddress()).isEqualTo(emailAddress);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);
    }

    @Test
    public void convertExistingUserToDomainUser()
    {
        long id = 1;
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";

        JpaUser jpaUser = new JpaUser();
        jpaUser.setId(id);
        jpaUser.setEmailAddress(emailAddress);
        jpaUser.setFirstName(firstName);
        jpaUser.setLastName(lastName);

        User user = userConverter.convertToDomain(jpaUser);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getEmailAddress()).isEqualTo(emailAddress);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);
    }

    @Test
    public void convertNullToDomainUser()
    {
        User user = userConverter.convertToDomain(null);
        assertThat(user).isNull();
    }
}
