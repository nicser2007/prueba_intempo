package co.microservices.domain.request;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RequestDTO {
    private String user;
    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
