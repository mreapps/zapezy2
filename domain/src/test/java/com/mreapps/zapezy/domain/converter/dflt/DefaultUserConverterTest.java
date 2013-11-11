package com.mreapps.zapezy.domain.converter.dflt;

import com.mreapps.zapezy.dao.entity.user.JpaRole;
import com.mreapps.zapezy.dao.entity.user.JpaUser;
import com.mreapps.zapezy.dao.repository.JpaRoleRepository;
import com.mreapps.zapezy.domain.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Tests {@link DefaultUserConverter}
 */
public class DefaultUserConverterTest
{
    @Mock
    private JpaRoleRepository roleRepository;

    private DefaultUserConverter userConverter;


    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        userConverter = new DefaultUserConverter();

        ReflectionTestUtils.setField(userConverter, "roleRepository", roleRepository);
    }

    @Test
    public void convertToJpaUser()
    {
        long id = 1;
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";
        String role = "admin";

        User user = new User(id);
        user.setEmailAddress(emailAddress);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);

        when(roleRepository.findByCode(any(String.class))).thenReturn(createRole(role));

        JpaUser jpaUser = userConverter.convertToDao(user);
        assertThat(jpaUser).isNotNull();
        assertThat(jpaUser.getId()).isEqualTo(id);
        assertThat(jpaUser.getEmailAddress()).isEqualTo(emailAddress);
        assertThat(jpaUser.getFirstName()).isEqualTo(firstName);
        assertThat(jpaUser.getLastName()).isEqualTo(lastName);
        assertThat(jpaUser.getRole().getCode()).isEqualTo(role);

        String toString = String.format(
            "JpaUser[id=%d, emailAddress='%s', firstName='%s', lastName='%s', role='%s']",
            id, emailAddress, firstName, lastName, user.getRole());
        assertThat(jpaUser.toString()).isEqualTo(toString);
    }

    @Test
    public void convertToJpaUserNullRole()
    {
        long id = 1;
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";
        String role = null;

        User user = new User(id);
        user.setEmailAddress(emailAddress);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);

        when(roleRepository.findByCode(any(String.class))).thenReturn(createRole(role));

        JpaUser jpaUser = userConverter.convertToDao(user);
        assertThat(jpaUser).isNotNull();
        assertThat(jpaUser.getRole()).isNull();
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
        String role= "admin";

        JpaUser jpaUser = new JpaUser();
        jpaUser.setEmailAddress(emailAddress);
        jpaUser.setFirstName(firstName);
        jpaUser.setLastName(lastName);
        jpaUser.setRole(createRole(role));

        User user = userConverter.convertToDomain(jpaUser);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getEmailAddress()).isEqualTo(emailAddress);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getRole()).isEqualTo(role);
    }

    @Test
    public void convertExistingUserToDomainUser()
    {
        long id = 1;
        String emailAddress = "user@zapezy.com";
        String firstName = "Ryan";
        String lastName = "Giggs";
        String role = "admin";

        JpaUser jpaUser = new JpaUser();
        jpaUser.setId(id);
        jpaUser.setEmailAddress(emailAddress);
        jpaUser.setFirstName(firstName);
        jpaUser.setLastName(lastName);
        jpaUser.setRole(createRole(role));

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

    private JpaRole createRole(String code)
    {
        JpaRole jpaRole = new JpaRole();
        jpaRole.setCode(code);
        return jpaRole;
    }
}
