package co.microservices.controller;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */


import co.microservices.domain.request.RequestReferenciaFacturaDTO;
import co.microservices.domain.request.RequestUserDTO;
import co.microservices.domain.response.ResponseConveniosDTO;
import co.microservices.domain.response.ResponseFormasPagoDTO;
import co.microservices.domain.response.ResponseReferenciaFacturaDTO;
import co.microservices.domain.response.ResponseUserDTO;
import co.microservices.service.PruebaIntempoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

import static co.microservices.util.ConstantsHelper.*;

@Slf4j
@RestController
@RequestMapping(SERVER_CONTEXT_PATH)
public class PruebaController {

    private final PruebaIntempoService pruebaIntempoService;

    public PruebaController(PruebaIntempoService pruebaIntempoService) {
        this.pruebaIntempoService = pruebaIntempoService;
    }

    @GetMapping(value = PING_YML_ROUTE)
    public final String ping() {
        return "it's alive Prueba Intempo";
    }

    @PostMapping(value = VALIDATE_USER)
    public ResponseUserDTO validateUser(@RequestBody RequestUserDTO requestUserDTO) {
        ResponseUserDTO responseUserDTO = pruebaIntempoService.validateUser(requestUserDTO);

        return responseUserDTO;
    }

    @GetMapping(value = LIST_CONVENIOS)
    public List<ResponseConveniosDTO> listConvenios() {
        return pruebaIntempoService.listConvenios();
    }

    @GetMapping(value = LIST_FORMAS_PAGO)
    public List<ResponseFormasPagoDTO> listFormasPago() {
        return pruebaIntempoService.listFormasPago();
    }

    @PostMapping(value = REFERENCIA_FACTURA)
    public Mono<ResponseReferenciaFacturaDTO> referenciaFactura(@RequestBody RequestReferenciaFacturaDTO request) {
        return pruebaIntempoService.referenciaFacturaGas(request);
    }

}
