package com.mogaco.project.global.config.security;

import com.mogaco.project.auth.application.AuthenticationService;
import com.mogaco.project.global.config.security.filter.AuthenticationErrorFilter;
import com.mogaco.project.global.config.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.Filter;

/**
 * Spring Security 설정.
 */
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final AuthenticationService authenticationService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    Filter authenticationFilter = new JwtAuthenticationFilter(
            authenticationManager(), authenticationService);
    Filter authenticationErrorFilter = new AuthenticationErrorFilter();

    http.csrf()
            .disable()
            .cors()
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
            .addFilter(authenticationFilter)
            .addFilterBefore(authenticationErrorFilter,
                    JwtAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/api/auth").permitAll()
            .antMatchers("/api/health-check").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/meets/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/members").permitAll()
            .antMatchers(HttpMethod.PATCH, "/api/v1/members/{id}")
            .access("@authenticationGuard.checkIdMatch(authentication,#id)")
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
            .antMatchers("/build/static/**")
            .antMatchers("/static/**")
            .antMatchers("/favicon.ico")
            .antMatchers("/manifest.json")
            .antMatchers("/docs/index/html");
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
