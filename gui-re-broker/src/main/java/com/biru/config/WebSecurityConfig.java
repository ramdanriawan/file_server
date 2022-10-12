package com.biru.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.client.RestTemplate;

import com.biru.component.SessionComponent;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("test");
		http
		.httpBasic()
        .and()
        .authorizeRequests()
//        .antMatchers("/**").permitAll()
//        .antMatchers("/login", "/vendors/**", "/build/**").permitAll()
//        .antMatchers(HttpMethod.GET, "/**").hasRole("USER")
//        .antMatchers(HttpMethod.POST, "/**").hasRole("USER")
        .antMatchers("/login/**", "/vendor/**", "/js/**", "/css/**", "/scss/**").permitAll()
        .antMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN001", "ADMIN002", "MRKT001", "FINA001", "OPER001", "OPER002")
        .antMatchers(HttpMethod.POST, "/**").hasAnyRole("ADMIN001", "ADMIN002", "MRKT001", "FINA001", "OPER001", "OPER002")
        .and()
        .csrf().disable()
		.formLogin()
		.loginPage("/login/form")
		.defaultSuccessUrl("/", true)
        .failureUrl("/login/form?error=true")
        .usernameParameter("username")
        .passwordParameter("password")
		.permitAll()
		.and()
		.logout()
		.logoutUrl("/logout")
        .logoutSuccessUrl("/login/form")
        .deleteCookies("JSESSIONID")
        .clearAuthentication(true)
		.permitAll()
		.and()
		.sessionManagement().maximumSessions(1).expiredUrl("/login/form");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//		.passwordEncoder(passwordEncoder())
//		.withUser("admin")
//		.password(passwordEncoder().encode("admin"))
//		.roles("USER");
		auth.userDetailsService(inMemoryUserDetailsManager());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	} 
	
	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
		
		System.out.println(passwordEncoder().encode("test"));
		List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
		userDetailsList.add(User.withUsername("admin").password(passwordEncoder().encode("admin"))
				.roles("ADMIN001").build());//, "ADMIN002", "MRKT001", "FINA001", "OPER001", "OPER002"
//		userDetailsList.add(User.withUsername("manager").password(passwordEncoder().encode("password"))
//				.roles("MANAGER", "USER").build());

		return new InMemoryUserDetailsManager(userDetailsList);
	}
}
