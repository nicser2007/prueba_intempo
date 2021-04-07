package co.microservices.util;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

public final class ConstantsHelper {

    // Yml properties
    public static final String APPLICATION_NAME = "${spring.application.name}";
    public static final String SERVER_CONTEXT_PATH = "${server.servlet.context-path}";
    public static final String PING_YML_ROUTE = "${spring.application.services.rest.ping}";
    public static final String VALIDATE_USER = "${spring.application.services.rest.validateUser}";
    public static final String LIST_CONVENIOS = "${spring.application.services.rest.listConvenios}";
    public static final String LIST_FORMAS_PAGO = "${spring.application.services.rest.listFormasPago}";
    public static final String REFERENCIA_FACTURA = "${spring.application.services.rest.referenciaFactura}";
    public static final String PAGO_FACTURA = "${spring.application.services.rest.pagoFactura}";
    public static final String COMPENSACION_FACTURA = "${spring.application.services.rest.compensacionFactura}";

    private ConstantsHelper() {
        // Not is necessary this implementation
    }

}
