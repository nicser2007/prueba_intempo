package co.microservices.domain;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

public enum OperationType {
    PAGO("Pago"),
    COMPENSACION("Compensacion");

    private final String type;

    OperationType(String type) {
        this.type = type;
    }

    public String getType(){return this.type;}
}
