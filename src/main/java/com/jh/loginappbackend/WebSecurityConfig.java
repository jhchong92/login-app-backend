package com.jh.loginappbackend;

import com.jh.loginappbackend.controller.HelloController;
import com.jh.loginappbackend.controller.LoginController;
import com.jh.loginappbackend.controller.ProfileController;
import com.jh.loginappbackend.controller.RegistrationController;
import com.jh.loginappbackend.security.CookieSecurityContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
  private final UserDetailsService userDetailsService;
  private final CookieSecurityContextRepository cookieSecurityContextRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors().disable();

    // disable login form
    http.formLogin().disable();

    http.securityContext().securityContextRepository(cookieSecurityContextRepository);
    // disable session creation
    http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()
        .antMatchers(HttpMethod.GET, HelloController.REQUEST_MAPPING_PATH).permitAll()
        .antMatchers(HttpMethod.POST, RegistrationController.REQUEST_MAPPING_PATH).permitAll()
        .antMatchers(HttpMethod.POST, LoginController.REQUEST_MAPPING_PATH).permitAll()
        .antMatchers(HttpMethod.GET, ProfileController.REQUEST_MAPPING_PATH).authenticated()
        .anyRequest().denyAll();

    return http.build();
  }

}
