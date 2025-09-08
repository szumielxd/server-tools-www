package me.szumielxd.tools_panel.database.config;

import me.szumielxd.tools_panel.entity.auth.AuthUser;
import me.szumielxd.tools_panel.repository.auth.AuthUserRepository;
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
		basePackageClasses = AuthUserRepository.class,
		entityManagerFactoryRef = "authEntityManagerFactory",
		transactionManagerRef = "authTransactionManager"
)
public class AuthDBConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean authEntityManagerFactory(@Qualifier("authDataSource") DataSource datasource, EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(datasource)
				.packages(AuthUser.class)
				.build();
	}

	@Bean
	public PlatformTransactionManager authTransactionManager(@Qualifier("authEntityManagerFactory") LocalContainerEntityManagerFactoryBean factory) {
		return new JpaTransactionManager(Objects.requireNonNull(factory.getObject()));
	}


}
