package co.microservices.config;

import co.microservices.domain.request.RequestClientDTO;
import co.microservices.wsdl.ResultadoConsulta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfiguration {

    @Bean
    public Jaxb2Marshaller getCitiesByCountryMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("co.microservices.wsdl");
        return marshaller;
    }

    @Bean
    public RequestClientDTO getReferenciaFacturaWSClient(Jaxb2Marshaller getReferenciaFactura) {
        RequestClientDTO requestClientDTO = new RequestClientDTO();
        requestClientDTO.setMarshaller(getReferenciaFactura);
        requestClientDTO.setUnmarshaller(getReferenciaFactura);
        requestClientDTO.setDefaultUri("http://130.211.116.156/gas-service/PagosService");
        return requestClientDTO;
    }
}
