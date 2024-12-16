package com.nouhoun.springboot.jwt.integration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * Configures the OAuth2 Resource Server.
 * This class defines the resource ID and configures the HTTP security for protected resources.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private ResourceServerTokenServices tokenServices;

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

    /**
     * Configures the resource server.
     * Sets the resource ID and the token services used to validate access tokens.
     *
     * @param resources The resource server security configurer.
     * @throws Exception If there is an error configuring the resource server.
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceIds).tokenServices(tokenServices);
    }

    /**
     * Configures the HTTP security for the resource server.
     * Defines which endpoints are protected and require authentication.
     * In this configuration, the /actuator/** and /api-docs/** endpoints are permitted without authentication,
     * while all endpoints under /springjwt/** require authentication.
     *
     * @param http The HTTP security object.
     * @throws Exception If there is an error configuring the HTTP security.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
                http
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**", "/api-docs/**").permitAll()
                .antMatchers("/springjwt/**" ).authenticated();
    }
}
