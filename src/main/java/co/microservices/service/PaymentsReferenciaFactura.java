package co.microservices.service;

import co.microservices.domain.request.RequestPagoFacturaDTO;
import co.microservices.domain.request.RequestReferenciaFacturaDTO;
import co.microservices.domain.response.ResponseConsultaAguaDTO;
import co.microservices.properties.ClientProperties;
import co.microservices.properties.SoapProperties;
import co.microservices.util.ReactiveConnector;
import co.microservices.util.SoapConnector;
import co.microservices.wsdl.Pago;
import co.microservices.wsdl.ReferenciaFactura;
import co.microservices.wsdl.Resultado;
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
public class PaymentsReferenciaFactura extends WebServiceGatewaySupport {

    private final SoapProperties soapProperties;
    private final ClientProperties clientProperties;
    private final SoapConnector accessClientSoap;
    private final ReactiveConnector clientAgua;
    private final ObjectMapper objectMapper;

    public PaymentsReferenciaFactura(SoapProperties soapProperties, ClientProperties clientProperties, SoapConnector accessClientSoap, ReactiveConnector clientAgua, ObjectMapper objectMapper) {
        this.soapProperties = soapProperties;
        this.clientProperties = clientProperties;
        this.accessClientSoap = accessClientSoap;
        this.clientAgua = clientAgua;
        this.objectMapper = objectMapper;
    }

    public Mono<Resultado> paymentsReferenciaFactura(RequestPagoFacturaDTO request) {

        if (request.getConvenio().equals("Gas")) {
            Pago pago = new Pago();
            ReferenciaFactura ref = new ReferenciaFactura();
            ref.setReferenciaFactura(request.getReferenciaFactura());
            pago.setReferenciaFactura(ref);
            pago.setTotalPagar(request.getValorFactura());

            return resultadoPagoGas(pago);
        } else if (request.getConvenio().equals("Agua")) {
            String url = clientProperties.getUrl().replace("{segment}", request.getReferenciaFactura());
            RequestPagoFacturaDTO pagoFacturaDTO = new RequestPagoFacturaDTO();
            pagoFacturaDTO.setValorFactura(request.getValorFactura());
            return resultadoPagoAgua(url, pagoFacturaDTO);
        }
        return null;
    }

    public Mono<Resultado> resultadoPagoGas(Pago request) {

        try {
            Resultado responseDTO = new Resultado();
            //Consumo del servicio por Giro
            return accessClientSoap.send(request, Resultado.class, soapProperties.getSoapAction())
                    .onErrorResume(e -> {
                        ReferenciaFactura ref = new ReferenciaFactura();
                        ref.setReferenciaFactura("0");
                        responseDTO.setReferenciaFactura(ref);
                        responseDTO.setMensaje("Fallido");
                        return Mono.just(responseDTO);
                    });


        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    public Mono<Resultado> resultadoPagoAgua(String url, RequestPagoFacturaDTO request) {

        try {
            Resultado responseDTO = new Resultado();

            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            //Consumo del servicio convenio Agua
            return clientAgua.exchange(HttpMethod.POST, url, request, String.class, MediaType.APPLICATION_JSON, header)
                    .flatMap(response -> {
                        ResponseConsultaAguaDTO responseConsultaAguaDTO;
                        try {
                            responseConsultaAguaDTO = objectMapper.readValue((String) response,ResponseConsultaAguaDTO.class);

                            ReferenciaFactura ref = new ReferenciaFactura();
                            ref.setReferenciaFactura(responseConsultaAguaDTO.getIdFactura().toString());
                            responseDTO.setReferenciaFactura(ref);
                            responseDTO.setMensaje(responseConsultaAguaDTO.getMensaje());
                            return Mono.just(responseDTO);
                        } catch (Exception e) {
                            ReferenciaFactura ref = new ReferenciaFactura();
                            ref.setReferenciaFactura("0");
                            responseDTO.setReferenciaFactura(ref);
                            responseDTO.setMensaje("Pago Fallido");
                            return Mono.just(responseDTO);
                        }

                    });
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

}
