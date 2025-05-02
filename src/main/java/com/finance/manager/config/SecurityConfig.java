
package com.finance.manager.config;

import com.finance.manager.services.JpaUserDetailsService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JpaUserDetailsService jpaUserDetailsService;
    private final RsaKeyProperties rsaKeys;
    private final JwtAuthenticationConverter jwtAuthConverter;
    private final Environment env;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService,
                          RsaKeyProperties rsaKeys,
                          JwtAuthenticationConverter jwtAuthConverter,
                          Environment env) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.rsaKeys = rsaKeys;
        this.jwtAuthConverter = jwtAuthConverter;
        this.env = env;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        boolean isDev = Arrays.asList(env.getActiveProfiles()).contains("dev");

        return http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                )
                .authorizeHttpRequests(auth -> {
                        // Public endpoints
                        auth.requestMatchers("/api/auth/authenticate").permitAll();

                        if (isDev) {
                            auth.requestMatchers("/docs.html").permitAll();
                            auth.requestMatchers("/swagger-ui.html").permitAll();
                            auth.requestMatchers("/swagger-ui/**").permitAll();
                            auth.requestMatchers("/v3/api-docs").permitAll();
                            auth.requestMatchers("/actuator/**").permitAll();
                        }

                        auth.anyRequest().authenticated();
                    }
                )
                .userDetailsService(jpaUserDetailsService)
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(token -> token
                                .jwtAuthenticationConverter(jwtAuthConverter))
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
