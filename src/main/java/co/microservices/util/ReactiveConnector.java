package co.microservices.util;

import com.sun.istack.NotNull;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import reactor.core.publisher.Mono;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.ConnectionProvider.Builder;
import reactor.netty.http.client.HttpClient;

import java.io.Serializable;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ReactiveConnector implements Serializable {
    private static final long serialVersionUID = 1535390408458475868L;
    private static final int HTTP_POOL_TOTAL_MAX = 500;
    private static final int HTTP_POOL_CHECK_INACTIVITY_IN_MILLIS = 300000;
    private final transient WebClient client;

    public ReactiveConnector(@NotNull String connectorName, @NotNull String puri, @NotNull Integer connectionTimeoutMillis, @NotNull Integer readTimeoutMillis) {
        ConnectionProvider connectionPool = ((Builder)((Builder)((Builder)((Builder)((Builder)ConnectionProvider.builder(connectorName).maxConnections(500)).pendingAcquireTimeout(Duration.ofMillis(0L))).pendingAcquireMaxCount(-1)).maxIdleTime(Duration.ofMillis(300000L))).maxLifeTime(Duration.ofMillis(300000L))).build();
        HttpClient httpClient = HttpClient.create(connectionPool).tcpConfiguration((tcpClient) -> {
            return tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeoutMillis).doOnConnected((conn) -> {
                conn.addHandlerLast(new ReadTimeoutHandler((long)readTimeoutMillis, TimeUnit.MILLISECONDS)).addHandlerLast(new WriteTimeoutHandler((long)readTimeoutMillis, TimeUnit.MILLISECONDS));
            });
        });
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        this.client = WebClient.builder().baseUrl(puri).clientConnector(connector).build();
    }

    private static Consumer<HttpHeaders> createHeaders(Map<String, String> headers) {
        return (httpHeaders) -> {
            if (headers != null && headers.size() > 0) {
                Iterator var2 = headers.entrySet().iterator();

                while(var2.hasNext()) {
                    Entry<String, String> head = (Entry)var2.next();
                    httpHeaders.set((String)head.getKey(), (String)head.getValue());
                }
            }

        };
    }

    public final Mono<Object> post(Object params, Class responseClass) {
        return ((RequestBodySpec)this.client.post().uri("/", new Object[0])).body(BodyInserters.fromValue(params)).retrieve().bodyToMono(responseClass);
    }

    public final Mono<Object> post(Object params, Class responseClass, MediaType mediaType, Map<String, String> headers) {
        return this.post("/", params, responseClass, mediaType, headers);
    }

    public final Mono<Object> post(Object params, Class responseClass, MediaType mediaType, MediaType acceptType, Map<String, String> headers) {
        return this.post("/", params, responseClass, mediaType, acceptType, headers);
    }

    public final Mono<Object> post(String endpoint, Object params, Class responseClass, MediaType mediaType, Map<String, String> headers) {
        return this.post(endpoint, params, responseClass, mediaType, mediaType, headers);
    }

    public final Mono<Object> post(String endpoint, Object params, Class responseClass, MediaType contentType, MediaType acceptType, Map<String, String> headers) {
        MediaType media = MediaType.APPLICATION_JSON;
        if (contentType != null) {
            media = contentType;
        }

        MediaType accept = media;
        if (acceptType != null) {
            accept = contentType;
        }

        return ((RequestBodySpec)((RequestBodySpec)((RequestBodySpec)this.client.post().uri(endpoint, new Object[0])).contentType(media).accept(new MediaType[]{accept})).headers(createHeaders(headers))).body(BodyInserters.fromValue(params)).retrieve().bodyToMono(responseClass);
    }

    public final Mono<Object> get(Class responseClass, MediaType mediaType, Map<String, String> headers) {
        return this.get("/", responseClass, mediaType, headers);
    }

    public final Mono<Object> get(String endpoint, Class responseClass, MediaType mediaType, Map<String, String> headers) {
        MediaType media = MediaType.APPLICATION_JSON;
        if (mediaType != null) {
            media = mediaType;
        }

        return this.client.get().uri(endpoint, new Object[0]).accept(new MediaType[]{media}).headers(createHeaders(headers)).retrieve().bodyToMono(responseClass);
    }

    public final Mono<Object> exchange(HttpMethod method, Object params, Class responseClass, MediaType mediaType, Map<String, String> headers) {
        return this.exchange(method, "/", params, responseClass, mediaType, headers);
    }

    public final Mono<Object> exchange(HttpMethod method, String endpoint, Object params, Class responseClass, MediaType mediaType, Map<String, String> headers) {
        MediaType media = MediaType.APPLICATION_JSON;
        if (mediaType != null) {
            media = mediaType;
        }

        return ((RequestBodySpec)((RequestBodySpec)((RequestBodySpec)this.client.method(method).uri(endpoint, new Object[0])).contentType(media).accept(new MediaType[]{media})).headers(createHeaders(headers))).body(BodyInserters.fromValue(params)).retrieve().bodyToMono(responseClass);
    }

    public final Mono<ClientResponse> exchange(HttpMethod method, String endpoint, Object params, MediaType mediaType, Map<String, String> headers) {
        MediaType media = MediaType.APPLICATION_JSON;
        if (mediaType != null) {
            media = mediaType;
        }

        return ((RequestBodySpec)((RequestBodySpec)((RequestBodySpec)this.client.method(method).uri(endpoint, new Object[0])).contentType(media).accept(new MediaType[]{media})).headers(createHeaders(headers))).body(BodyInserters.fromValue(params)).exchange();
    }
}
