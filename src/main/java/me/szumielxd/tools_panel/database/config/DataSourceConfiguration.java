package me.szumielxd.tools_panel.database.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.auth")
	public DataSourceProperties authDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public @NonNull DataSource authDataSource() {
		return authDataSourceProperties()
				.initializeDataSourceBuilder()
				.build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.moderation")
	public DataSourceProperties moderationDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	public @NonNull DataSource moderationDataSource() {
		return moderationDataSourceProperties()
				.initializeDataSourceBuilder()
				.type(com.zaxxer.hikari.HikariDataSource.class)
				.build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.logs")
	public DataSourceProperties playerLogDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public @NonNull DataSource playerLogDataSource() {
		return playerLogDataSourceProperties()
				.initializeDataSourceBuilder()
				.type(com.zaxxer.hikari.HikariDataSource.class)
				.build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.portfel")
	public DataSourceProperties portfelDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public @NonNull DataSource portfelDataSource() {
		return portfelDataSourceProperties()
				.initializeDataSourceBuilder()
				.type(com.zaxxer.hikari.HikariDataSource.class)
				.build();
	}

}
