package me.szumielxd.tools_panel.database.config;

import me.szumielxd.tools_panel.repository.log.PlayerLogRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackageClasses = PlayerLogRepository.class,
		entityManagerFactoryRef = "playerLogEntityManagerFactory",
		transactionManagerRef = "playerLogTransactionManager"
)
public class PlayerLogDBConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean playerLogEntityManagerFactory(@Qualifier("playerLogDataSource") DataSource datasource, EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(datasource)
				.packages("")
				.build();
	}

	@Bean
	public PlatformTransactionManager playerLogTransactionManager(@Qualifier("playerLogEntityManagerFactory") LocalContainerEntityManagerFactoryBean factory) {
		return new JpaTransactionManager(Objects.requireNonNull(factory.getObject()));
	}

	@Bean(name = "playerLogJdbcTemplate")
	public JdbcTemplate playerLogJdbcTemplate(@Qualifier("playerLogDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}


}
