package com.mreapps.zapezy.dao.entity.common;

import com.mreapps.zapezy.core.entity.Language;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests {@link JpaText}
 */
public class JpaTextTest
{
    @Test
    public void gettersAndSetters()
    {
        JpaText jpaText = new JpaText();
        jpaText.setText(Language.NORWEGIAN, "Norwegian text");
        jpaText.setText(Language.ENGLISH, "English text");

        assertThat(jpaText.getText(Language.NORWEGIAN)).isEqualTo("Norwegian text");
        assertThat(jpaText.getText(Language.ENGLISH)).isEqualTo("English text");
    }

    @Test
    public void getterWithNull()
    {
        boolean caught = false;
        try
        {
            new JpaText().getText(null);
        } catch (IllegalArgumentException e)
        {
            caught = true;
            assertThat(e.getMessage()).isEqualTo("Unsupported language: null");
        }
        assertThat(caught).isTrue();
    }

    @Test
    public void setterWithNull()
    {
        boolean caught = false;
        try
        {
            new JpaText().setText(null, "test");
        } catch (IllegalArgumentException e)
        {
            caught = true;
            assertThat(e.getMessage()).isEqualTo("Unsupported language: null");
        }
        assertThat(caught).isTrue();
    }
}
