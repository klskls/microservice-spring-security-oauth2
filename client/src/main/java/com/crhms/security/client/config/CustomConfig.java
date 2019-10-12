package com.crhms.security.client.config;

import com.crhms.security.client.security.ServiceOAuth2RestTemplate;
import com.crhms.security.client.security.SsoLogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.context.request.RequestContextListener;

import java.util.Arrays;

/**
 * @author ：hkk
 * @date ：Created in 2019/10/11 9:56
 */
@Configuration
public class CustomConfig {

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean("serviceClientCredentialsResourceDetails")
    @ConditionalOnMissingBean(name = "serviceClientCredentialsResourceDetails")
    @ConfigurationProperties("cis-service-security.oauth2.client")
    public ClientCredentialsResourceDetails details() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        return details;
    }


    @Bean("serviceOAuth2RestTemplate")
    @ConditionalOnMissingBean(name = "serviceOAuth2RestTemplate")
    public ServiceOAuth2RestTemplate cisServiceOAuth2RestTemplate(@Qualifier("serviceClientCredentialsResourceDetails") ClientCredentialsResourceDetails details) {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        final ServiceOAuth2RestTemplate oAuth2RestTemplate = new ServiceOAuth2RestTemplate(details,new DefaultOAuth2ClientContext(atr));

        ClientCredentialsAccessTokenProvider authCodeProvider = new ClientCredentialsAccessTokenProvider();

        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(authCodeProvider));

        oAuth2RestTemplate.setAccessTokenProvider(provider);

        return oAuth2RestTemplate;
    }

}