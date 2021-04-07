package co.microservices.util;

import com.sun.istack.NotNull;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.support.MarshallingUtils;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBElement;
import java.io.Serializable;

public class SoapConnector extends WebServiceGatewaySupport implements Serializable {

    private static final long serialVersionUID = 1535390408458475868L;
    private static final int HTTP_POOL_TOTAL_MAX = 200;
    private static final int HTTP_POOL_ROUTE_MAX = 30;
    private static final int HTTP_POOL_CHECK_INACTIVITY_IN_MILLIS = 500;
    private final String uri;

    public SoapConnector(@NotNull String puri, @NotNull Integer connectionTimeoutMillis, @NotNull Integer readTimeoutMillis, @NotNull Jaxb2Marshaller marshaller) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectionTimeoutMillis).setConnectionRequestTimeout(readTimeoutMillis).setSocketTimeout(readTimeoutMillis).setCookieSpec("standard").build();
        PoolingHttpClientConnectionManager connectionPool = new PoolingHttpClientConnectionManager();
        connectionPool.setMaxTotal(200);
        connectionPool.setDefaultMaxPerRoute(30);
        connectionPool.setValidateAfterInactivity(500);
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setConnectionManager(connectionPool).setSSLHostnameVerifier(new NoopHostnameVerifier()).addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor()).build();
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender(httpClient);
        this.getWebServiceTemplate().setMessageSender(messageSender);
        this.getWebServiceTemplate().setMarshaller(marshaller);
        this.getWebServiceTemplate().setUnmarshaller(marshaller);
        this.uri = puri;
    }

    public SoapConnector(@NotNull String puri, @NotNull Integer connectionTimeoutMillis, @NotNull Integer readTimeoutMillis, @NotNull Jaxb2Marshaller marshaller, @NotNull ClientInterceptor... clientInterceptors) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectionTimeoutMillis).setConnectionRequestTimeout(readTimeoutMillis).setSocketTimeout(readTimeoutMillis).setCookieSpec("standard").build();
        PoolingHttpClientConnectionManager connectionPool = new PoolingHttpClientConnectionManager();
        connectionPool.setMaxTotal(200);
        connectionPool.setDefaultMaxPerRoute(30);
        connectionPool.setValidateAfterInactivity(500);
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setConnectionManager(connectionPool).setSSLHostnameVerifier(new NoopHostnameVerifier()).addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor()).build();
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender(httpClient);
        this.getWebServiceTemplate().setMessageSender(messageSender);
        this.getWebServiceTemplate().setMarshaller(marshaller);
        this.getWebServiceTemplate().setUnmarshaller(marshaller);
        this.setInterceptors(clientInterceptors);
        this.uri = puri;
    }

    public String getUri() {
        return this.uri;
    }

    public final Object call(Object request) {
        return this.getWebServiceTemplate().marshalSendAndReceive(this.uri, request);
    }

    public final Object call(Object request, WebServiceMessageCallback messageCallback) {
        return this.getWebServiceTemplate().marshalSendAndReceive(this.uri, request, messageCallback);
    }

    public final Object call(Object requestPayload, WebServiceMessageCallback requestCallback, WebServiceMessageExtractor extractor) {
        return this.getWebServiceTemplate().sendAndReceive(this.uri, (request) -> {
            if (requestPayload != null) {
                Marshaller marshaller = this.getMarshaller();
                if (marshaller == null) {
                    throw new IllegalStateException("No marshaller registered. Check configuration of WebServiceTemplate.");
                }

                MarshallingUtils.marshal(marshaller, requestPayload, request);
                if (requestCallback != null) {
                    requestCallback.doWithMessage(request);
                }
            }

        }, extractor);
    }

    public Object call(Object request, String soapAction) {
        JAXBElement resp = (JAXBElement)this.getWebServiceTemplate().marshalSendAndReceive(this.uri, request, new SoapActionCallback(soapAction));
        return resp.getValue();
    }

    public final <I, O> Mono<O> send(I request, Class<O> outClass) {
        return null == request ? Mono.error(new NullPointerException("Request is required.")) : Mono.fromSupplier(() -> {
            JAXBElement<O> result = (JAXBElement)this.getWebServiceTemplate().marshalSendAndReceive(this.uri, request);
            return result.getValue();
        });
    }

    public final <I, O> Mono<O> send(I request, Class<O> outClass, String soapAction) {
        return null == request ? Mono.error(new NullPointerException("Request is required.")) : Mono.fromSupplier(() -> {
            JAXBElement<O> result = (JAXBElement)this.getWebServiceTemplate().marshalSendAndReceive(this.uri, request, new SoapActionCallback(soapAction));
            return result.getValue();
        });
    }
}
