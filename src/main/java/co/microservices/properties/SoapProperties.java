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
public class SoapProperties {

    // CONNECTION
    @Value("${providers.gas.url}")
    private String url;

    @Value("${providers.gas.soapAction}")
    private String soapAction;

    @Value("${providers.gas.timeout.connection}")
    private int connectiontimeout;

    @Value("${providers.gas.timeout.read}")
    private int readTimeout;

    @Value("${providers.gas.contextPath}")
    private String contextPath;

    public String getUrl() {
        return url;
    }

    public String getSoapAction() {
        return soapAction;
    }

    public int getConnectiontimeout() {
        return connectiontimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public String getContextPath() {
        return contextPath;
    }
}
