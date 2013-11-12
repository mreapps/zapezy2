package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.user.JpaRole;
import com.mreapps.zapezy.dao.entity.user.JpaUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration("/spring/DaoTestContext.xml")
public class JpaUserRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests
{
    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private JpaRoleRepository roleRepository;

    @Test
    public void findByEmailAddress()
    {
        String emailAddress = "user@zapezy.com";
        String encryptedPassword = "encryptedPassword";
        String lastName = "Ryan";
        String firstName = "Giggs";

        JpaUser user = new JpaUser();
        user.setEmailAddress(emailAddress);
        user.setEncryptedPassword(encryptedPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(createRole());
        user.setUserRegisteredDate(new Date());
        userRepository.persist(user);

        user = userRepository.findByEmailAddress(emailAddress);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmailAddress()).isEqualTo(emailAddress);
        assertThat(user.getEncryptedPassword()).isEqualTo(encryptedPassword);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);

        String toString = String.format(
                "JpaUser[id=%d, emailAddress='%s', firstName='%s', lastName='%s', role='%s']",
                user.getId(), emailAddress, firstName, lastName, user.getRole().getCode());
        assertThat(user.toString()).isEqualTo(toString);

        user = userRepository.findByEmailAddress("wrong_email");
        assertThat(user).isNull();
    }

    @Test
    public void findByEmailConfirmationToken()
    {
        String emailAddress = "user@zapezy.com";
        String encryptedPassword = "encryptedPassword";
        String lastName = "Ryan";
        String firstName = "Giggs";
        String emailConfirmationToken = "1234567890";

        JpaUser user = new JpaUser();
        user.setEmailAddress(emailAddress);
        user.setEncryptedPassword(encryptedPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(createRole());
        user.setUserRegisteredDate(new Date());
        user.setEmailConfirmationToken(emailConfirmationToken);
        userRepository.persist(user);

        user = userRepository.findByEmailConfirmationToken(emailConfirmationToken);
        assertThat(user).isNotNull();
        assertThat(user.getEmailAddress()).isEqualTo(emailAddress);
        assertThat(user.getEmailConfirmationToken()).isEqualTo(emailConfirmationToken);
        assertThat(user.isEmailConfirmed()).isFalse();

        user = userRepository.findByEmailConfirmationToken(emailConfirmationToken + "wrong");
        assertThat(user).isNull();
    }

    @Test
    public void storeWithFixedId()
    {
        long id = 1;
        String emailAddress = "user@zapezy.com";
        String lastName = "Ryan";
        String firstName = "Giggs";
        Date date = new Date();

        JpaUser user = new JpaUser();
        user.setId(id);
        user.setEmailAddress(emailAddress);
        user.setEncryptedPassword("...");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailConfirmed(true);
        user.setEmailConfirmedDate(date);
        user.setRole(createRole());
        user.setUserRegisteredDate(date);
        userRepository.merge(user);

        user = userRepository.get(id);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getEmailConfirmedDate()).isEqualTo(date);
        assertThat(user.getUserRegisteredDate()).isEqualTo(date);
    }

    @Test
    public void remove()
    {
        String emailAddress = "user@zapezy.com";

        JpaUser user = new JpaUser();
        user.setEmailAddress(emailAddress);
        user.setEncryptedPassword("...");
        user.setFirstName("Ryan");
        user.setLastName("Giggs");
        user.setRole(createRole());
        user.setUserRegisteredDate(new Date());
        userRepository.persist(user);

        user = userRepository.findByEmailAddress(emailAddress);
        assertThat(user).isNotNull();
        assertThat(user.getEmailAddress()).isEqualTo(emailAddress);

        userRepository.remove(user);
        user = userRepository.findByEmailAddress(emailAddress);
        assertThat(user).isNull();
    }

    private JpaRole createRole()
    {
        JpaRole role = new JpaRole();
        role.setCode("admin");
        roleRepository.persist(role);

        return role;
    }
}
