package com.mreapps.zapezy.dao.config;

import org.junit.Test;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.test.util.ReflectionTestUtils;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests different settings in {@link JpaPersistenceConfiguration}
 */
public class JpaPersistenceConfigurationTest
{
    @Test
    public void dataSourceHsql()
    {
        JpaPersistenceConfiguration jpaPersistenceConfiguration = new JpaPersistenceConfiguration();
        ReflectionTestUtils.setField(jpaPersistenceConfiguration, "vendor", Database.HSQL);
        assertThat(jpaPersistenceConfiguration.dataSource()).isNotNull();
    }

    @Test
    public void dataSourcePostgresql()
    {
        JpaPersistenceConfiguration jpaPersistenceConfiguration = new JpaPersistenceConfiguration();
        ReflectionTestUtils.setField(jpaPersistenceConfiguration, "vendor", Database.POSTGRESQL);
        assertThat(jpaPersistenceConfiguration.dataSource()).isNotNull();
    }

    @Test
    public void dataSourceUnsupported()
    {
        boolean caught = false;
        try
        {
            JpaPersistenceConfiguration jpaPersistenceConfiguration = new JpaPersistenceConfiguration();
            assertThat(jpaPersistenceConfiguration.dataSource()).isNotNull();
        }
        catch (IllegalArgumentException e)
        {
            assertThat(e.getMessage()).isEqualTo("Unsupported database vendor: null");
            caught = true;
        }
        assertThat(caught).isTrue();
    }
}
