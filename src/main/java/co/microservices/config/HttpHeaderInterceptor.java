package co.microservices.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

@Slf4j
public final class HttpHeaderInterceptor implements ClientInterceptor {


    public HttpHeaderInterceptor() {
        super();
    }

    public boolean handleFault(MessageContext messageContext) {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e)  {
        // Do nothing because of X and Y.
    }

    public boolean handleResponse(MessageContext messageContext){
        return true;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext){
        return true;
    }
}
