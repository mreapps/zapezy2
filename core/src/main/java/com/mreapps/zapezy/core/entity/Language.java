package com.mreapps.zapezy.core.entity;

/**
 * Language enum. I.e. used for texts in different languages
 */
public enum Language
{
    NORWEGIAN((short) 1, "norwegian"),
    ENGLISH((short) 2, "english");

    private short id;
    private String resourceKey;

    private Language(short id, String resourceKey)
    {
        this.id = id;
        this.resourceKey = resourceKey;
    }

    public short getId()
    {
        return id;
    }

    public String getResourceKey()
    {
        return resourceKey;
    }

    /**
     * @param id The unique language id
     * @return The language with the specified id
     * @throws IllegalArgumentException if the id is not known
     */
    public static Language getById(short id)
    {
        for (Language language : values())
        {
            if (language.id == id)
            {
                return language;
            }
        }
        throw new IllegalArgumentException("Unsupported id: " + id);
    }
}
