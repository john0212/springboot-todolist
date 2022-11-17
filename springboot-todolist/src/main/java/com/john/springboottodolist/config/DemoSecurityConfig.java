package com.john.springboottodolist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	// 這是再說要用 Spring Security 使用此數據源進行 JDBC 身份驗證。
	@Qualifier("securityDataSource")
	private DataSource securityDataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// add our users for in memory authentication
//		UserBuilder users = User.withDefaultPasswordEncoder();
//
//		auth.inMemoryAuthentication()
//			.withUser(users.username("john").password("test123").roles("USER"))
//			.withUser(users.username("mary").password("test123").roles("USER", "ADMIN"));

		// use jdbc authentication
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/tasks/showForm*").hasAnyRole("ADMIN","USER")
			.antMatchers("/tasks/save*").hasAnyRole("USER", "ADMIN")
			.antMatchers("/tasks/delete").hasRole("ADMIN")
			.antMatchers("/tasks/**").hasRole("USER")
			.antMatchers("/resources/**").permitAll()
			.and()
			.formLogin()
				.loginPage("/showMyLoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");
		
	}
		
}






