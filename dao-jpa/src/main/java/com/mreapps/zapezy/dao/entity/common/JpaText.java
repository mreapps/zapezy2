package com.mreapps.zapezy.dao.entity.common;

import com.mreapps.zapezy.core.entity.Language;

import javax.persistence.Embeddable;

/**
 * Embeddable entity to handle texts in different languages
 */
@Embeddable
public class JpaText
{
    private String norwegianText;
    private String englishText;

    /**
     * Returns the text in the specified language
     *
     * @param language The language to get the text for
     * @return The text
     */
    public String getText(Language language)
    {
        if (language == Language.NORWEGIAN)
        {
            return norwegianText;
        } else if (language == Language.ENGLISH)
        {
            return englishText;
        } else
        {
            throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    /**
     * Sets the text for the specified language
     *
     * @param language The language to set the text for
     * @param text     The text to set
     */
    public void setText(Language language, String text)
    {
        if (language == Language.NORWEGIAN)
        {
            this.norwegianText = text;
        } else if (language == Language.ENGLISH)
        {
            this.englishText = text;
        } else
        {
            throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}
