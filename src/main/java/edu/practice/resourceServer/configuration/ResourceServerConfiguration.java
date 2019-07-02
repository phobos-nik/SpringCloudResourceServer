package edu.practice.resourceServer.configuration;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private DefaultAccessTokenConverter defaultAccessTokenConverter;

    @Value("${JWT_ACCESS_TOKEN_SIGNING_KEY:default_signing_key}")
    private String jwtAccessTokenSigningKey;

    @Value("${REMOTE_TOKEN_SERVICE_URL:localhost}")
    private String remoteTokenServiceUrl;

    @Value("${AUTHORIZATION_CLIENT_ID:default_authorization_client_id}")
    private String authorizationClientId;

    @Value("${AUTHORIZATION_CLIENT_SECRET:default_authorization_client_secret}")
    private String authorizationClientSecret;

    @Autowired
    public ResourceServerConfiguration(DefaultAccessTokenConverter defaultAccessTokenConverter) {
        this.defaultAccessTokenConverter = defaultAccessTokenConverter;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
        jwtAccessTokenConverter.setSigningKey(jwtAccessTokenSigningKey);
        return jwtAccessTokenConverter;
    }

    @Bean
    public RemoteTokenServices remoteTokenServices() throws MalformedURLException {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();

        URL remoteTokenService = new URL("http://" + remoteTokenServiceUrl);
        URL checkTokenEndpointUrl = new URL(remoteTokenService, "/oauth/check_token");

        remoteTokenServices.setCheckTokenEndpointUrl(checkTokenEndpointUrl.toString());
        remoteTokenServices.setClientId(authorizationClientId);
        remoteTokenServices.setClientSecret(authorizationClientSecret);
        return remoteTokenServices;
    }
}
