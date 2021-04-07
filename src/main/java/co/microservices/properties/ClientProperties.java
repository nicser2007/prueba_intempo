package co.microservices.properties;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ClientProperties {

    // CONNECTION
    @Value("${providers.agua.url}")
    private String url;

    @Value("${providers.agua.timeout.connection}")
    private int connectiontimeout;

    @Value("${providers.agua.timeout.read}")
    private int readTimeout;

    public String getUrl() {
        return url;
    }

    public int getConnectiontimeout() {
        return connectiontimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }
}
