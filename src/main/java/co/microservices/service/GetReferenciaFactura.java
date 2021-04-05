package co.microservices.service;

import co.microservices.config.SoapWSExecutionCallBack;
import co.microservices.domain.request.RequestReferenciaFacturaDTO;
import co.microservices.properties.SoapProperties;
import co.microservices.wsdl.ReferenciaFactura;
import co.microservices.wsdl.ResultadoConsulta;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class GetReferenciaFactura extends WebServiceGatewaySupport {

    private final SoapProperties soapProperties;

    public GetReferenciaFactura(SoapProperties soapProperties) {
        this.soapProperties = soapProperties;
    }

    public ResultadoConsulta getReferenciaFactura (RequestReferenciaFacturaDTO request){

        ReferenciaFactura referenciaFactura = new ReferenciaFactura();
        referenciaFactura.setReferenciaFactura(request.getReferenciaFactura());

        return (ResultadoConsulta) getWebServiceTemplate().marshalSendAndReceive(request,
                new SoapWSExecutionCallBack(soapProperties.getSoapAction()));

    }

}
