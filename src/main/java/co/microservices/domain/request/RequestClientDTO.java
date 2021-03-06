package co.microservices.domain.request;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class RequestClientDTO {

    private String defaultUri;
    private Jaxb2Marshaller marshaller;
    private Jaxb2Marshaller unmarshaller;

    public String getDefaultUri() {
        return defaultUri;
    }

    public void setDefaultUri(String defaultUri) {
        this.defaultUri = defaultUri;
    }

    public Jaxb2Marshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public Jaxb2Marshaller getUnmarshaller() {
        return unmarshaller;
    }

    public void setUnmarshaller(Jaxb2Marshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }
}
