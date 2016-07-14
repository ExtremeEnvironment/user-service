package de.extremeenvironment.userservice.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by on 14.07.16.
 *
 * @author David Steiman
 */
@Configuration
@Profile("!test")
@EnableFeignClients(basePackages = "de.extremeenvironment.userservice.client")
public class FeignConfiguration {
}
