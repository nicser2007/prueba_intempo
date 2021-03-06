package co.microservices.domain.response;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode()
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"id", "name","compensar"})
public class ResponseConveniosDTO {

    @Schema(example = "1", description = "identificador del convenio")
    private BigDecimal id;

    @Schema(example = "Agua", description = "Nombre del convenio")
    private String name;

    @Schema(example = "S", description = "Indicador si el convenio tiene reverso")
    private String compensar;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompensar() {
        return compensar;
    }

    public void setCompensar(String compensar) {
        this.compensar = compensar;
    }
}
