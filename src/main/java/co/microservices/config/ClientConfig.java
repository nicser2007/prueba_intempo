package co.microservices.config;

import co.microservices.properties.ClientProperties;
import co.microservices.util.ReactiveConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Configuration
public class ClientConfig {

    @Bean(name="clientAgua")
    public ReactiveConnector clientAgua(@NotNull ClientProperties clientProperties){
        return new ReactiveConnector("clientAgua", clientProperties.getUrl(), clientProperties.getConnectiontimeout(), clientProperties.getReadTimeout());
    }
}
