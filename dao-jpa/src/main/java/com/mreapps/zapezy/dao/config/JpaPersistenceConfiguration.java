package com.mreapps.zapezy.dao.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * Configuration for setting up persistence towards databases from different vendors
 */
@Configuration
public class JpaPersistenceConfiguration
{
    private static final int IDLE_CONNECTION_TEST_PERIOD = 240;
    private static final int IDLE_MAX_AGE = 60;
    private static final int MAX_CONNECTIONS_PER_PARTITION = 20;
    private static final int MIN_CONNECTIONS_PER_PARTITION = 2;
    private static final int PARTITION_COUNT = 2;
    private static final int ACQUIRE_INCREMENT = 2;

    @Value("${jdbc.vendor}")
    private Database vendor;

    @Value("${jdbc.database}")
    private String database;

    @Value("${jdbc.hostname}")
    private String hostname;

    @Value("${jdbc.port}")
    private int port;

    @Value("${jdbc.user}")
    private String user;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jpa.showSql}")
    private boolean showSql;

    @Value("${jpa.generateDdl}")
    private boolean generateDdl;

    @Value("${jpa.packages}")
    private String packages;

    /**
     * @return The data source with connection infomation on a specific database
     */
    @Bean
    public DataSource dataSource()
    {
        final BoneCPDataSource dataSource = new BoneCPDataSource();
        final String jdbcDriverClass;
        final String jdbcUrl;
        if (vendor == Database.HSQL)
        {
            jdbcDriverClass = "org.hsqldb.jdbcDriver";
            jdbcUrl = String.format(
                    "jdbc:hsqldb:mem:%s",
                    database);
        } else if (vendor == Database.POSTGRESQL)
        {
            jdbcDriverClass = "org.postgresql.Driver";
            jdbcUrl = String.format(
                    "jdbc:postgresql://%s:%d/%s",
                    hostname, port, database);
        } else
        {
            throw new IllegalArgumentException("Unsupported database vendor: " + vendor);
        }
        dataSource.setDriverClass(jdbcDriverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        dataSource.setIdleConnectionTestPeriod(IDLE_CONNECTION_TEST_PERIOD, TimeUnit.MINUTES);
        dataSource.setIdleMaxAge(IDLE_MAX_AGE, TimeUnit.MINUTES);
        dataSource.setMaxConnectionsPerPartition(MAX_CONNECTIONS_PER_PARTITION);
        dataSource.setMinConnectionsPerPartition(MIN_CONNECTIONS_PER_PARTITION);
        dataSource.setPartitionCount(PARTITION_COUNT);
        dataSource.setAcquireIncrement(ACQUIRE_INCREMENT);

        dataSource.setCloseOpenStatements(false);
        dataSource.setDisableConnectionTracking(true);
        return dataSource;
    }

    /**
     * @return The JpaVendorAdapter contains information on showSql, generateDdl and vendor
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter()
    {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(showSql);
        hibernateJpaVendorAdapter.setGenerateDdl(generateDdl);
        hibernateJpaVendorAdapter.setDatabase(vendor);
        return hibernateJpaVendorAdapter;
    }

    /**
     * @param dataSource       Data source with database information
     * @param jpaVendorAdapter The JpaVendorAdapter contains information on showSql, generateDdl and vendor
     * @return An EntityManagerFactory is used to create {@link javax.persistence.EntityManager} for database sessions
     */
    @Bean
    @DependsOn({"dataSource", "jpaVendorAdapter"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter)
    {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan(packages);
        return lef;
    }

    /**
     * @param entityManagerFactory An EntityManagerFactory is used to create {@link javax.persistence.EntityManager} for database sessions
     * @return TransactionManager to handle commit and rollback
     */
    @Bean
    @DependsOn({"entityManagerFactory"})
    public org.springframework.transaction.PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory)
    {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }
}
