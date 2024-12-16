package com.nouhoun.springboot.jwt.integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/**
 * Spring Security configuration class.
 * This class enables web security, global method security (prePostEnabled),
 * configures session management, HTTP basic authentication, and disables CSRF.
 * It also defines beans for the password encoder, access token converter, token store, and token services.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.signing-key}")
    private String signingKey;

    @Value("${security.encoding-strength}")
    private Integer encodingStrength;

    @Value("${security.security-realm}")
    private String securityRealm;


    /**
     * Overrides the authenticationManager bean.
     *
     * @return The authentication manager.
     * @throws Exception If there is an error creating the authentication manager.
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * Creates a BCryptPasswordEncoder bean for password encoding.
     *
     * @return The BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the HTTP security settings.
     * Sets up stateless session management, HTTP basic authentication with a specified realm,
     * and disables CSRF protection.
     *
     * @param http The HttpSecurity object to configure.
     * @throws Exception If there is an error configuring HTTP security.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .realmName(securityRealm)
                .and()
                .csrf()
                .disable();

    }

    /**
     * Creates a JwtAccessTokenConverter bean for converting between JWTs and access tokens.
     * Sets the signing key for the converter.
     *
     * @return The JwtAccessTokenConverter instance.
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    /**
     * Creates a TokenStore bean for storing access tokens.
     * Uses a JwtTokenStore to store tokens as JWTs.
     *
     * @return The TokenStore instance.
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * Creates a DefaultTokenServices bean.
     * Sets the token store and enables refresh token support.
     * The @Primary annotation ensures that this instance is used when multiple token services are available.
     *
     * @return The DefaultTokenServices instance.
     */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}
