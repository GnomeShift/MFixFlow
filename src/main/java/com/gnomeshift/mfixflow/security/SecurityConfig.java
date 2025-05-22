package com.gnomeshift.mfixflow.security;

import com.gnomeshift.mfixflow.security.util.AuthEntryPoint;
import com.gnomeshift.mfixflow.security.util.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final AuthEntryPoint unauthHandler;

    public SecurityConfig(UserDetailsService userDetailsService, AuthEntryPoint unauthHandler) {
        this.userDetailsService = userDetailsService;
        this.unauthHandler = unauthHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/users/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/requests").hasRole("MASTER")
                                .requestMatchers("/api/requests").authenticated()
                                .requestMatchers("/api/requests/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/requests/**/logs").hasAnyAuthority("MASTER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/requests/logs").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/defects").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/defects/**").authenticated()
                                .requestMatchers("/api/defects").hasAnyAuthority("MASTER", "ADMIN")
                                .requestMatchers("/api/defects/**").hasAnyAuthority("MASTER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/devices").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/devices/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/devices").hasAnyAuthority("MASTER", "ADMIN")
                                .requestMatchers("/api/devices/**").hasAnyAuthority("MASTER", "ADMIN")
                                .requestMatchers("/api/roles").hasRole("ADMIN")
                                .requestMatchers("/api/roles/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
