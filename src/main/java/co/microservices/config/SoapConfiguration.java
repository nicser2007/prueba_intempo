package co.microservices.config;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

import co.microservices.properties.SoapProperties;
import co.microservices.util.SoapConnector;
import com.sun.istack.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfiguration {

    private final SoapProperties soapProperties;

    public SoapConfiguration(SoapProperties soapProperties) {
        this.soapProperties = soapProperties;
    }

    @Bean
    public HttpHeaderInterceptor httpHeaderInterceptor() {
        return new HttpHeaderInterceptor();
    }

    @Bean
    public Jaxb2Marshaller accessMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(soapProperties.getContextPath());
        return marshaller;
    }

    @Bean
    public SoapConnector accessClientSoap(@NotNull Jaxb2Marshaller accessMarshaller, @NotNull HttpHeaderInterceptor httpHeaderInterceptor) {
        return new SoapConnector(
                soapProperties.getUrl(),
                soapProperties.getConnectiontimeout(),
                soapProperties.getReadTimeout(),
                accessMarshaller,
                httpHeaderInterceptor
        );
    }
}
