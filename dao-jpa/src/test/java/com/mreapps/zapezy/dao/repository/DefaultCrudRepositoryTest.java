package com.mreapps.zapezy.dao.repository;

import com.mreapps.zapezy.dao.repository.dflt.DefaultCrudRepository;
import org.junit.Test;

import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests events like null result set or multiple results on find single to be assured that the errors is handled properly
 */
public class DefaultCrudRepositoryTest
{
    @Test
    public void multiResultOnFindSingle()
    {
        boolean caught = false;
        try
        {
            new DefaultCrudRepository<String>()
            {
                public void findSingle()
                {
                    findSingle(null);
                }

                @Override
                protected List<String> findMany(CriteriaQuery cq, int startPosition, int maxResult)
                {
                    return Arrays.asList("1", "2", "3");
                }
            }.findSingle();
        }
        catch (NonUniqueResultException e)
        {
            assertThat(e.getMessage()).isEqualTo("Multiple entities returned from findSingle");
            caught = true;
        }
        assertThat(caught).isTrue();
    }

    @Test
    public void nullResultOnFindSingle()
    {
        String result = new DefaultCrudRepository<String>()
        {
            public String findSingle()
            {
                return findSingle(null);
            }

            @Override
            protected List<String> findMany(CriteriaQuery cq, int startPosition, int maxResult)
            {
                return null;
            }
        }.findSingle();
        assertThat(result).isNull();
    }

    @Test
    public void emptyResultOnFindSingle()
    {
        String result = new DefaultCrudRepository<String>()
        {
            public String findSingle()
            {
                return findSingle(null);
            }

            @Override
            protected List<String> findMany(CriteriaQuery cq, int startPosition, int maxResult)
            {
                return Arrays.asList();
            }
        }.findSingle();
        assertThat(result).isNull();
    }
}
