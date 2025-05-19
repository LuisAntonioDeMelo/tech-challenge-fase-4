package com.techchallenge.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/actuator/**", "/actuator").permitAll()
                        .pathMatchers("/fallback/**").permitAll()
                        .pathMatchers("/api/*/health", "/api/*/swagger-ui/**", "/api/*/api-docs/**").permitAll()
                        .pathMatchers("/eureka/**").hasRole("ADMIN")

                        .pathMatchers(HttpMethod.GET,  "/api/pedidos/**").permitAll()
                        .pathMatchers(HttpMethod.POST,  "/api/pedidos/**").permitAll()
                        .pathMatchers(HttpMethod.PUT,  "/api/pedidos/**").permitAll()
                        .pathMatchers(HttpMethod.DELETE,  "/api/pedidos/**").hasRole("ADMIN")
                        
                        .pathMatchers(HttpMethod.GET, "/api/pagamentos/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/pagamentos/**").permitAll()
                        .pathMatchers(HttpMethod.PUT, "/api/pagamentos/**").permitAll()
                        .pathMatchers(HttpMethod.DELETE,  "/api/pagamentos/**").hasRole("ADMIN")
                        
                        .pathMatchers(HttpMethod.GET, "/api/clientes/**").permitAll()
                        .pathMatchers(HttpMethod.POST,  "/api/clientes/**").permitAll()
                        .pathMatchers(HttpMethod.PUT,  "/api/clientes/**").permitAll()
                        .pathMatchers(HttpMethod.DELETE, "/api/clientes/**").hasRole("ADMIN")

                        .pathMatchers(HttpMethod.GET, "/api/produtos/**").permitAll()
                        .pathMatchers(HttpMethod.POST,  "/api/produtos/**").permitAll()
                        .pathMatchers(HttpMethod.PUT,  "/api/produtos/**").permitAll()
                        .pathMatchers(HttpMethod.DELETE, "/api/produtos/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .httpBasic(httpBasic -> {})
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN", "USER")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("usuario")
                .password("senha123")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(admin, user);
    }
}
