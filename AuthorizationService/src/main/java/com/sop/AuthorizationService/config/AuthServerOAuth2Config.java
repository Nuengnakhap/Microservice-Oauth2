package com.sop.AuthorizationService.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("userService")
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder oauthClientPasswordEncoder;

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Bean
	public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
		return new OAuth2AccessDeniedHandler();
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	// Credentials for angular front end client and resource server.
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.jdbc(dataSource);
		clients.inMemory().withClient("client_id").secret(new BCryptPasswordEncoder().encode("client_password"))
		.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("read", "write")
		.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "USER", "ADMIN").autoApprove(true)
		.accessTokenValiditySeconds(18000)// Access token is only valid for 3 minutes.
		.refreshTokenValiditySeconds(60000);// Refresh token is only valid for 10 minutes.;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.tokenStore(tokenStore())
			.authenticationManager(authenticationManager)
			.accessTokenConverter(defaultAccessTokenConverter())
			.userDetailsService(userDetailsService);
		endpoints.pathMapping("/oauth/token", "/login");
	}
	
	@Bean
	public JwtAccessTokenConverter defaultAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("123");
		return converter;
	}

}