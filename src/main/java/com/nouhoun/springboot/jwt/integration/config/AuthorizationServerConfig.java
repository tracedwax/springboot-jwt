package com.nouhoun.springboot.jwt.integration.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Configures the OAuth2 Authorization Server.
 * This class sets up the authorization server to issue JWTs, defines an in-memory client
 * for demonstration (you should use a persistent store in production), and configures the endpoints
 * for token generation.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite = "write";

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Configures the client details service.  This method uses an in-memory client details store
	 * for demonstration.  In a production application, you should use a persistent store like a database.
	 * This configuration sets the client ID, secret, authorized grant type, scopes, and resource IDs.
	 *
	 * @param configurer The client details service configurer.
	 * @throws Exception If there is an error configuring the client details service.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
		        .inMemory()
		        .withClient(clientId)
				.secret(passwordEncoder.encode(clientSecret))
		        .authorizedGrantTypes(grantType)
		        .scopes(scopeRead, scopeWrite)
		        .resourceIds(resourceIds);
	}

	/**
	 * Configures the authorization server endpoints.  This includes setting the token store,
	 * the access token converter (for JWTs), a token enhancer chain (which includes the JWT converter),
	 * and the authentication manager.
	 *
	 * @param endpoints The authorization server endpoints configurer.
	 * @throws Exception If there is an error configuring the endpoints.
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		endpoints.tokenStore(tokenStore)
		        .accessTokenConverter(accessTokenConverter)
		        .tokenEnhancer(enhancerChain)
		        .authenticationManager(authenticationManager);
	}

}
