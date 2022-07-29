package com.exportech.encriptografarusuario.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.exportech.encriptografarusuario.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class JTWConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsServiceImpl userService;
  private final PasswordEncoder passwordEncoder;

  public JTWConfiguration(UserDetailsServiceImpl userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
  }
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
    .antMatchers(HttpMethod.POST, "/login").permitAll()
    .anyRequest().authenticated()
    .and()
    .addFilter(new JTWAutenticatorFilter(authenticationManager()))
    .addFilter(new JWTValidateSecurity(authenticationManager()))
    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource (){
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }
  
}
