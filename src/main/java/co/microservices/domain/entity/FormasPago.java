package co.microservices.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "formas_pago", schema = "banco_abc")
public class FormasPago {

    @Id
    @Column(name = "id", nullable = false)
    private BigDecimal id;

    @Column(name = "description", nullable = false)
    private String description;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}