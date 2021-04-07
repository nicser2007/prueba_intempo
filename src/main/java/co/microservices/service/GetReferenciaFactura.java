package co.microservices.service;

import co.microservices.domain.request.RequestReferenciaFacturaDTO;
import co.microservices.domain.response.ResponseConsultaAguaDTO;
import co.microservices.domain.response.ResponseReferenciaFacturaDTO;
import co.microservices.properties.ClientProperties;
import co.microservices.properties.SoapProperties;
import co.microservices.util.ReactiveConnector;
import co.microservices.util.SoapConnector;
import co.microservices.wsdl.ReferenciaFactura;
import co.microservices.wsdl.ResultadoConsulta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetReferenciaFactura extends WebServiceGatewaySupport {

    private final SoapProperties soapProperties;
    private final ClientProperties clientProperties;
    private final SoapConnector accessClientSoap;
    private final ReactiveConnector clientAgua;
    private final ObjectMapper objectMapper;

    public GetReferenciaFactura(SoapProperties soapProperties, ClientProperties clientProperties, SoapConnector accessClientSoap, ReactiveConnector clientAgua, ObjectMapper objectMapper) {
        this.soapProperties = soapProperties;
        this.clientProperties = clientProperties;
        this.accessClientSoap = accessClientSoap;
        this.clientAgua = clientAgua;
        this.objectMapper = objectMapper;
    }

    public Mono<ResultadoConsulta> getReferenciaFactura(RequestReferenciaFacturaDTO request) {

        if (request.getConvenio().equals("Gas")) {
            ReferenciaFactura referenciaFactura = new ReferenciaFactura();
            referenciaFactura.setReferenciaFactura(request.getReferenciaFactura());

            return resultadoConsultaGas(referenciaFactura);
        } else if (request.getConvenio().equals("Agua")) {
            String url = clientProperties.getUrl().replace("{segment}", request.getReferenciaFactura());
            return resultadoConsultaAgua(url);
        }
        return null;
    }

    public Mono<ResultadoConsulta> resultadoConsultaGas(ReferenciaFactura request) {

        try {
            ResultadoConsulta responseDTO = new ResultadoConsulta();
            //Consumo del servicio por Giro
            return accessClientSoap.send(request, ResultadoConsulta.class, soapProperties.getSoapAction())
                    .onErrorResume(e -> {
                        ReferenciaFactura ref = new ReferenciaFactura();
                        ref.setReferenciaFactura("0");
                        responseDTO.setReferenciaFactura(ref);
                        responseDTO.setTotalPagar(0);
                        return Mono.just(responseDTO);
                    });


        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    public Mono<ResultadoConsulta> resultadoConsultaAgua(String url) {

        try {
            ResultadoConsulta responseDTO = new ResultadoConsulta();

            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            //Consumo del servicio convenio Agua
            return clientAgua.exchange(HttpMethod.GET, url, "", String.class, MediaType.APPLICATION_JSON, header)
                    .flatMap(response -> {
                        ResponseConsultaAguaDTO responseConsultaAguaDTO;
                        try {
                            responseConsultaAguaDTO = objectMapper.readValue((String) response,ResponseConsultaAguaDTO.class);

                            ReferenciaFactura ref = new ReferenciaFactura();
                            ref.setReferenciaFactura(responseConsultaAguaDTO.getIdFactura().toString());
                            responseDTO.setReferenciaFactura(ref);
                            responseDTO.setTotalPagar(responseConsultaAguaDTO.getValorFactura());
                            return Mono.just(responseDTO);
                        } catch (Exception e) {
                            ReferenciaFactura ref = new ReferenciaFactura();
                            ref.setReferenciaFactura("0");
                            responseDTO.setReferenciaFactura(ref);
                            responseDTO.setTotalPagar(0);
                            return Mono.just(responseDTO);
                        }

                    });
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

}
