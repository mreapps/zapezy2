package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.entity.JpaUser;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration("/spring/DaoTestContext.xml")
public class JpaUserRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests
{
    @Autowired
    private JpaUserRepository userRepository;

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
        userRepository.persist(user);

        user = userRepository.findByEmailAddress(emailAddress);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getEmailAddress()).isEqualTo(emailAddress);
        Assertions.assertThat(user.getEncryptedPassword()).isEqualTo(encryptedPassword);
        Assertions.assertThat(user.getFirstName()).isEqualTo(firstName);
        Assertions.assertThat(user.getLastName()).isEqualTo(lastName);

        String toString = String.format(
                "JpaUser[id=%d, emailAddress='%s', firstName='%s', lastName='%s']",
                user.getId(), emailAddress, firstName, lastName);
        Assertions.assertThat(user.toString()).isEqualTo(toString);

        user = userRepository.findByEmailAddress("wrong_email");
        Assertions.assertThat(user).isNull();
    }

    @Test
    public void storeWithFixedId()
    {
        long id = 1;
        String emailAddress = "user@zapezy.com";
        String lastName = "Ryan";
        String firstName = "Giggs";

        JpaUser user = new JpaUser();
        user.setId(id);
        user.setEmailAddress(emailAddress);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepository.merge(user);

        user = userRepository.get(id);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(id);
    }

    @Test
    public void remove()
    {
        String emailAddress = "user@zapezy.com";

        JpaUser user = new JpaUser();
        user.setEmailAddress(emailAddress);
        user.setFirstName("Ryan");
        user.setLastName("Giggs");
        userRepository.persist(user);

        user = userRepository.findByEmailAddress(emailAddress);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getEmailAddress()).isEqualTo(emailAddress);

        userRepository.remove(user);
        user = userRepository.findByEmailAddress(emailAddress);
        Assertions.assertThat(user).isNull();
    }
}
