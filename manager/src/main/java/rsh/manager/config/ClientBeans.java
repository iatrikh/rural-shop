package rsh.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import rsh.manager.client.RestClientProductsRestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductsRestClient productsRestClient(
            @Value("${rsh.services.catalog.uri:http://localhost:8081}") String catalogBaseUri) {
        return new RestClientProductsRestClient(RestClient
                .builder()
                .baseUrl(catalogBaseUri)
                .build());
    }
}
