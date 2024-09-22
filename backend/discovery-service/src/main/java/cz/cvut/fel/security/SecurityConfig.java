package cz.cvut.fel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class that defines how HTTP security is managed.
 *
 * This class disables CSRF protection, requires authentication for all requests,
 * and uses basic authentication with a custom realm name for HTTP basic auth.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain.
     *
     * <ul>
     *   <li>CSRF protection is disabled.</li>
     *   <li>All HTTP requests require authentication.</li>
     *   <li>Basic authentication is enabled with a custom realm name "DiscoveryRealm".</li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} object to configure security settings.
     * @return the configured {@link SecurityFilterChain} object.
     * @throws Exception if an error occurs while configuring the security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(httpBasic -> httpBasic
                        .realmName("DiscoveryRealm")
                )
                .build();
    }
}
