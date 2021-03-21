package sumaru.config;


import org.apache.tomcat.dbcp.dbcp2.managed.BasicManagedDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "sumaru.persistence" })
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {	
	
	@Autowired	
	UserDetailsService userDetailsService;
	
	@Autowired	
	private BasicManagedDataSource basicDataSource;

	@Autowired	
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("loko").password("123456")
				.roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("123456")
				.roles("ADMIN");
		auth.jdbcAuthentication().dataSource(basicDataSource)
	      .withDefaultSchema()
	      .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
	      .and()
	      .withUser("admin").password(passwordEncoder().encode("password")).roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests()
		.antMatchers("/profile/**").access("hasRole('USER')")
		.antMatchers("/admin/**").access("hasRole('ADMIN')")		
		.and().formLogin().loginPage("/login").defaultSuccessUrl("/profile")
		.and().logout().logoutSuccessUrl("/")	
		.and().exceptionHandling().accessDeniedPage("/403");
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}