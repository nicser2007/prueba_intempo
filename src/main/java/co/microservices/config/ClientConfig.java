package co.microservices.config;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

import co.microservices.properties.ClientProperties;
import co.microservices.util.ReactiveConnector;
import com.sun.istack.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean(name="clientAgua")
    public ReactiveConnector clientAgua(@NotNull ClientProperties clientProperties){
        return new ReactiveConnector("clientAgua", clientProperties.getUrl(), clientProperties.getConnectiontimeout(), clientProperties.getReadTimeout());
    }
}
