package com.nouhoun.springboot.jwt.integration.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Configuration class for the data source and JPA setup.
 * This configures an embedded H2 database, sets up the entity manager factory,
 * and defines the transaction manager.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.nouhoun.springboot.jwt.integration.repository")
public class DatasourceConfig {

    /**
     * Creates the embedded H2 data source.
     * It initializes the database with schema.sql and data.sql scripts.
     *
     * @return The embedded H2 data source.
     * @throws PropertyVetoException If there is an error configuring the data source.  (Not expected with embedded databases).
     */
    @Bean
    public DataSource datasource() throws PropertyVetoException {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase dataSource = builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("sql-scripts/schema.sql")
                .addScript("sql-scripts/data.sql")
                .build();

        return dataSource;
    }

    /**
     * Creates the entity manager factory.
     * Configures the data source, package scanning for entities, and the JPA vendor adapter (Hibernate).
     *
     * @param ds The data source.
     * @return The entity manager factory.
     * @throws PropertyVetoException If there's an issue with data source configuration (not expected with embedded databases).
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("datasource") DataSource ds) throws PropertyVetoException{
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(ds);
        entityManagerFactory.setPackagesToScan(new String[]{"com.nouhoun.springboot.jwt.integration.domain"});
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        return entityManagerFactory;
    }

    /**
     * Creates the transaction manager.
     * Uses the entity manager factory for managing transactions.
     *
     * @param entityManagerFactory The entity manager factory.
     * @return The transaction manager.
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
