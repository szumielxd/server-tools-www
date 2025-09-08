package me.szumielxd.tools_panel.database.config;

import me.szumielxd.tools_panel.entity.portfel.PortfelUser;
import me.szumielxd.tools_panel.repository.portfel.PortfelUserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackageClasses = PortfelUserRepository.class,
		entityManagerFactoryRef = "portfelEntityManagerFactory",
		transactionManagerRef = "portfelTransactionManager"
)
public class PortfelDBConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean portfelEntityManagerFactory(@Qualifier("portfelDataSource") DataSource datasource, EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(datasource)
				.packages(PortfelUser.class)
				.build();
	}

	@Bean
	public PlatformTransactionManager portfelTransactionManager(@Qualifier("portfelEntityManagerFactory") LocalContainerEntityManagerFactoryBean factory) {
		return new JpaTransactionManager(Objects.requireNonNull(factory.getObject()));
	}


}
