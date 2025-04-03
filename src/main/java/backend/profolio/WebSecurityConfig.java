package backend.profolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity(securedEnabled = true)


public class WebSecurityConfig {
	
	//@Autowired
	private UserDetailsService userDetailsService;
	
	// Constructor injection
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService; }
	
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authorize -> authorize
					.requestMatchers(antMatcher("/css/**")).permitAll()
					.requestMatchers(antMatcher("/h2-console/**")).permitAll()
					.requestMatchers(antMatcher("/signup")).permitAll()
					.requestMatchers(antMatcher("/saveuser")).permitAll()

					.anyRequest().authenticated())

			.headers(headers -> headers
					.frameOptions(frameOptions -> frameOptions
					.disable())) // for h2console
					.httpBasic(Customizer.withDefaults())
			
			.formLogin(formlogin -> formlogin
					.loginPage("/login")
					.defaultSuccessUrl("/projectlist", true)
					.permitAll())
			
			.logout(logout -> logout.permitAll())
			
			.csrf(csrf -> csrf.disable()); // not for production, just for development

		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
