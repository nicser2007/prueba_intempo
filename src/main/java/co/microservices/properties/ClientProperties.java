package co.microservices.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ClientProperties {

    // CONNECTION
    @Value("${providers.agua.url}")
    private String url;

    @Value("${providers.agua.timeout.connection}")
    private int connectiontimeout;

    @Value("${providers.agua.timeout.read}")
    private int readTimeout;

    public String getUrl() {
        return url;
    }

    public int getConnectiontimeout() {
        return connectiontimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }
}
