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
    public static final String REGISTER_MINTIC = "${schedulers.mintic.cron}";
    public static final String CRON_MINTIC_RIESGO = "${schedulers.mintic.cronRiesgo}";

    // Logs on start application
    public static final String LBLTIEMPO = "Tiempo empleado en el envío/respuesta de la operacion:  {}";

    public static final String OK = "OK";
    public static final String NO = "DISABLED";

    // Correlative
    static final String CORRELATIVE_ID = "correlation-id";
    static final String COMPONENT_CORRELATIVE = "component";

    public static final String NAMESPACE = "http://schemas.datacontract.org/2004/07/MINTIC.VYC.DTO";
    public static final String NAMESPACE_ARRAY = "http://schemas.microsoft.com/2003/10/Serialization/Arrays";

    private ConstantsHelper() {
        // Not is necessary this implementation
    }

}
