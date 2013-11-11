package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.core.entity.Language;
import com.mreapps.zapezy.dao.entity.user.JpaRole;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration("/spring/DaoTestContext.xml")
public class JpaRoleRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests
{
    @Autowired
    private JpaRoleRepository roleRepository;

    @Test
    public void findByCode()
    {
        createRoles();

        String code = "user";
        JpaRole jpaRole = roleRepository.findByCode(code);
        assertThat(jpaRole).isNotNull();
        assertThat(jpaRole.getCode()).isEqualTo(code);
        assertThat(jpaRole.getName().getText(Language.NORWEGIAN)).isEqualTo("Bruker");
        assertThat(jpaRole.getName().getText(Language.ENGLISH)).isEqualTo("User");
        assertThat(jpaRole.toString()).isEqualTo(
                String.format(
                        "JpaRole[id=%d, code='%s', nameNo='%s', nameEn='%s']",
                        jpaRole.getId(), code, jpaRole.getName().getText(Language.NORWEGIAN), jpaRole.getName().getText(Language.ENGLISH))
        );

        jpaRole = roleRepository.findByCode(code + "unknown");
        assertThat(jpaRole).isNull();
    }

    @Test
    public void findAll()
    {
        createRoles();

        List<JpaRole> allRoles = roleRepository.findAll();
        assertThat(allRoles).isNotNull();
        assertThat(allRoles.size()).isEqualTo(3);
    }

    private void createRoles()
    {
        createRole("admin", "Administrator", "Administrator");
        createRole("user", "Bruker", "User");
        createRole("anonymous", "Anonym", "Anonymous");
    }

    private void createRole(String code, String norwegianName, String englishName)
    {
        JpaRole jpaRole = new JpaRole();
        jpaRole.setCode(code);
        jpaRole.getName().setText(Language.NORWEGIAN, norwegianName);
        jpaRole.getName().setText(Language.ENGLISH, englishName);

        roleRepository.persist(jpaRole);
    }
}
