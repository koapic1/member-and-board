package com.jjang051.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.jjang051.model")
@MapperScan("com.jjang051.mybatis")

public class RootAppContext {
	
		
	
	
	
	
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName("oracle.jdbc.OracleDriver");
		hikariConfig.setJdbcUrl("jdbc:oracle:thin:@DB202201131452_medium?TNS_ADMIN=/Users/Shared/Wallet_DB/Wallet_DB202201131452");
		hikariConfig.setUsername("TIS02");
		hikariConfig.setPassword("abcdABCD1234");
		
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception  {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		PathMatchingResourcePatternResolver pathResolver =  new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setConfigLocation(pathResolver.getResource("classpath:com/jjang051/mybatis/config.xml"));
		
		sqlSessionFactoryBean.setMapperLocations(pathResolver.getResources("classpath:com/jjang051/mybatis/*Mapper.xml"));
		return (SqlSessionFactory)sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}









