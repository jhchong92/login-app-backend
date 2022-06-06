package com.jh.loginappbackend;

import com.jh.loginappbackend.controller.HelloController;
import com.jh.loginappbackend.controller.RegistrationController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors().disable();
    http.authorizeRequests()
        .antMatchers(HttpMethod.GET, HelloController.REQUEST_MAPPING_PATH).permitAll()
        .antMatchers(HttpMethod.POST, RegistrationController.REQUEST_MAPPING_PATH).permitAll()

        .anyRequest().denyAll();
        // .httpBasic(Customizer.withDefaults());
    return http.build();
  }

}
