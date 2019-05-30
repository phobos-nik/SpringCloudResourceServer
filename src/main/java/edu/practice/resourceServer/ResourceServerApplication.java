package edu.practice.resourceServer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ResourceServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ResourceServerApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
