package co.microservices.domain.entity;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "convenios", schema = "banco_abc")
public class Convenios {

    @Id
    @Column(name = "convenio_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "compensar", nullable = false)
    private String compensar;

    @Column(name = "status", nullable = false)
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
