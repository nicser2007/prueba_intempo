package co.microservices.controller;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */


import co.microservices.domain.request.RequestPagoFacturaDTO;
import co.microservices.domain.request.RequestReferenciaFacturaDTO;
import co.microservices.domain.request.RequestUserDTO;
import co.microservices.domain.response.ResponseConveniosDTO;
import co.microservices.domain.response.ResponseFormasPagoDTO;
import co.microservices.domain.response.ResponseReferenciaFacturaDTO;
import co.microservices.domain.response.ResponseUserDTO;
import co.microservices.service.PruebaIntempoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

import static co.microservices.util.ConstantsHelper.*;

@Slf4j
@RestController
@RequestMapping
public class PruebaController {

    private final PruebaIntempoService pruebaIntempoService;

    public PruebaController(PruebaIntempoService pruebaIntempoService) {
        this.pruebaIntempoService = pruebaIntempoService;
    }

    @GetMapping(value = PING_YML_ROUTE)
    public final ResponseEntity<String> ping() {
        String res = "it's alive Prueba Intempo";
        return ResponseEntity.ok(res);
    }

    @PostMapping(value = VALIDATE_USER)
    public ResponseEntity<ResponseUserDTO> validateUser(@RequestBody RequestUserDTO requestUserDTO) {
        ResponseUserDTO responseUserDTO = pruebaIntempoService.validateUser(requestUserDTO);
        return ResponseEntity.ok(responseUserDTO);
    }

    @GetMapping(value = LIST_CONVENIOS)
    public ResponseEntity<List<ResponseConveniosDTO>> listConvenios() {
        List<ResponseConveniosDTO> response = pruebaIntempoService.listConvenios();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = LIST_FORMAS_PAGO)
    public ResponseEntity<List<ResponseFormasPagoDTO>> listFormasPago() {
        List<ResponseFormasPagoDTO> response = pruebaIntempoService.listFormasPago();
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = REFERENCIA_FACTURA)
    public ResponseEntity<Mono<ResponseReferenciaFacturaDTO>> referenciaFactura(@RequestBody RequestReferenciaFacturaDTO request) {
        Mono<ResponseReferenciaFacturaDTO> response = pruebaIntempoService.referenciaFactura(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = PAGO_FACTURA)
    public ResponseEntity<Mono<ResponseReferenciaFacturaDTO>> pagoFactura(@RequestBody RequestPagoFacturaDTO request) {
        Mono<ResponseReferenciaFacturaDTO> response = pruebaIntempoService.pagoFactura(request);
        return ResponseEntity.ok(response);
    }

}
