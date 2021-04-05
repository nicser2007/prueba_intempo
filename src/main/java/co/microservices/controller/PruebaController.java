package co.microservices.controller;

import co.microservices.domain.entity.Convenios;
import co.microservices.domain.request.RequestDTO;
import co.microservices.domain.response.ResponseConveniosDTO;
import co.microservices.domain.response.ResponseFormasPagoDTO;
import co.microservices.domain.response.ResponseUserDTO;
import co.microservices.service.PruebaIntempoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public ResponseUserDTO validateUser(@RequestBody RequestDTO requestDTO){
        ResponseUserDTO responseUserDTO = pruebaIntempoService.validateUser(requestDTO);

        return responseUserDTO;
    }

    @GetMapping(value = LIST_CONVENIOS)
    public List<ResponseConveniosDTO> listConvenios(){
        return pruebaIntempoService.listConvenios();
    }

    @GetMapping(value = LIST_FORMAS_PAGO)
    public List<ResponseFormasPagoDTO> listFormasPago(){
        return pruebaIntempoService.listFormasPago();
    }

}
