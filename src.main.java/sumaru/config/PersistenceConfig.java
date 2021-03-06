package sumaru.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Properties;


import org.apache.tomcat.dbcp.dbcp2.managed.BasicManagedDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

	@Bean
	public LocalSessionFactoryBean sessionFactory()
			throws URISyntaxException, SQLException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory
				.setPackagesToScan(new String[] { "sumaru.persistence.domain" });
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	@Bean
	public BasicManagedDataSource dataSource() throws URISyntaxException {
		
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        String username = System.getenv("JDBC_DATABASE_USERNAME");
        String password = System.getenv("JDBC_DATABASE_PASSWORD");
        
        
		BasicManagedDataSource basicDataSource = new BasicManagedDataSource();
		//basicDataSource.setDriverClassName("org.postgresql.Driver");
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);

		return basicDataSource;
	}

	final Properties hibernateProperties() {
		final Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
		hibernateProperties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.PostgreSQLDialect");

		hibernateProperties.setProperty("hibernate.show_sql", "true");
		// hibernateProperties.setProperty("hibernate.format_sql", "true");
		// hibernateProperties.setProperty("hibernate.globally_quoted_identifiers",
		// "true");

		return hibernateProperties;
	}
	
	@Bean
	@Autowired
	public org.springframework.orm.hibernate5.HibernateTransactionManager transactionManager(
			final SessionFactory sessionFactory) {
		final org.springframework.orm.hibernate5.HibernateTransactionManager txManager = new org.springframework.orm.hibernate5.HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}
	

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	

}
