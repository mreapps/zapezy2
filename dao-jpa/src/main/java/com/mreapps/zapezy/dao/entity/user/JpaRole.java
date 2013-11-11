package com.mreapps.zapezy.dao.entity.user;

import com.mreapps.zapezy.core.entity.Language;
import com.mreapps.zapezy.dao.entity.AbstractJpaBaseEntity;
import com.mreapps.zapezy.dao.entity.common.JpaText;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * A role is used to restrict access in the application
 */
@Entity(name = "role")
public class JpaRole extends AbstractJpaBaseEntity
{
    public static final int MAX_CODE_LENGTH = 10;
    public static final int MAX_NAME_LENGTH = 30;

    @Column(unique = true, nullable = false, length = MAX_CODE_LENGTH)
    private String code;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "norwegianText", column = @Column(name = "name_no", length = MAX_NAME_LENGTH)),
            @AttributeOverride(name = "englishText", column = @Column(name = "name_en", length = MAX_NAME_LENGTH))
    })
    private JpaText name = new JpaText();

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public JpaText getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return String.format(
                "JpaRole[id=%d, code='%s', nameNo='%s', nameEn='%s']",
                getId(), code, name.getText(Language.NORWEGIAN), name.getText(Language.ENGLISH));
    }
}
