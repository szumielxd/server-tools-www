package me.szumielxd.tools_panel.database.config;

import me.szumielxd.tools_panel.entity.moderation.Moderator;
import me.szumielxd.tools_panel.repository.moderation.ModeratorRepository;
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
		basePackageClasses = ModeratorRepository.class,
		entityManagerFactoryRef = "moderationEntityManagerFactory",
		transactionManagerRef = "moderationTransactionManager"
)
public class ModerationDBConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean moderationEntityManagerFactory(@Qualifier("moderationDataSource") DataSource datasource, EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(datasource)
				.packages(Moderator.class)
				.build();
	}

	@Bean
	public PlatformTransactionManager moderationTransactionManager(@Qualifier("moderationEntityManagerFactory") LocalContainerEntityManagerFactoryBean factory) {
		return new JpaTransactionManager(Objects.requireNonNull(factory.getObject()));
	}


}
