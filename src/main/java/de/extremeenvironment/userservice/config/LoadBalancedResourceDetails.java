package de.extremeenvironment.userservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by on 14.07.16.
 *
 * @author David Steiman
 */

@Component
public class LoadBalancedResourceDetails extends ClientCredentialsResourceDetails {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public LoadBalancedResourceDetails(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;

        setAccessTokenUri(jHipsterProperties.getSecurity().getClientAuthorization().getTokenUrl());
        setClientId(jHipsterProperties.getSecurity().getClientAuthorization().getClientId());
        setClientSecret(jHipsterProperties.getSecurity().getClientAuthorization().getClientSecret());
        setGrantType("client_credentials");

    }

    private LoadBalancerClient loadBalancerClient;

    private JHipsterProperties jHipsterProperties;


    @Autowired(required = false)
    public void setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    @Override
    public String getAccessTokenUri() {
        String serviceName = jHipsterProperties.getSecurity().getClientAuthorization().getTokenServiceId();
        if (loadBalancerClient != null && !serviceName.isEmpty()) {
            String newUrl;
            try {
                newUrl = loadBalancerClient.reconstructURI(
                    loadBalancerClient.choose(serviceName),
                    new URI(super.getAccessTokenUri())
                ).toString();

                return newUrl;
            } catch (URISyntaxException e) {
                log.error("{}: {}", e.getClass().toString(), e.getMessage());

                return super.getAccessTokenUri();
            }
        } else {
            return super.getAccessTokenUri();
        }
    }
}
