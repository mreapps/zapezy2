package com.mreapps.zapezy.core.entity;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class LanguageTest
{
    @Test
    public void getById()
    {
        Language norwegian = Language.getById((short)1);
        assertThat(norwegian).isNotNull();
        assertThat(norwegian.getId()).isEqualTo((short)1);
        assertThat(norwegian.getResourceKey()).isEqualTo("norwegian");

        Language english = Language.getById((short)2);
        assertThat(english).isNotNull();
        assertThat(english.getId()).isEqualTo((short)2);
        assertThat(english.getResourceKey()).isEqualTo("english");
    }

    @Test
    public void getByIdIllegal()
    {
        boolean caught = false;
        try
        {
            Language.getById((short)-1);
        }
        catch (IllegalArgumentException e)
        {
            caught = true;
            assertThat(e.getMessage()).isEqualTo("Unsupported id: -1");
        }
        assertThat(caught).isTrue();
    }
}
