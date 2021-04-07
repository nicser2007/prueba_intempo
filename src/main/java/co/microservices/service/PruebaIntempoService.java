package co.microservices.service;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

import co.microservices.domain.OperationType;
import co.microservices.domain.entity.Convenios;
import co.microservices.domain.entity.FormasPago;
import co.microservices.domain.entity.User;
import co.microservices.domain.request.RequestPagoFacturaDTO;
import co.microservices.domain.request.RequestReferenciaFacturaDTO;
import co.microservices.domain.request.RequestUserDTO;
import co.microservices.domain.response.ResponseConveniosDTO;
import co.microservices.domain.response.ResponseFormasPagoDTO;
import co.microservices.domain.response.ResponseReferenciaFacturaDTO;
import co.microservices.domain.response.ResponseUserDTO;
import co.microservices.repository.jpa.IConveniosRepository;
import co.microservices.repository.jpa.IFormasPagoRepository;
import co.microservices.repository.jpa.IUserRepository;
import co.microservices.util.UtilDataBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PruebaIntempoService {

    private final IUserRepository userRepository;
    private final IConveniosRepository conveniosRepository;
    private final IFormasPagoRepository formasPagoRepository;
    private final GetReferenciaFactura getReferenciaFactura;
    private final PaymentsReferenciaFactura paymentsReferenciaFactura;
    private final UtilDataBase utilDataBase;

    public PruebaIntempoService(IUserRepository userRepository, IConveniosRepository conveniosRepository, IFormasPagoRepository formasPagoRepository, GetReferenciaFactura getReferenciaFactura, PaymentsReferenciaFactura paymentsReferenciaFactura, UtilDataBase utilDataBase) {
        this.userRepository = userRepository;
        this.conveniosRepository = conveniosRepository;
        this.formasPagoRepository = formasPagoRepository;
        this.getReferenciaFactura = getReferenciaFactura;
        this.paymentsReferenciaFactura = paymentsReferenciaFactura;
        this.utilDataBase = utilDataBase;
    }


    public ResponseUserDTO validateUser (RequestUserDTO requestUserDTO){
        log.info("Inicio Validacion de Usuario: User: {}", requestUserDTO.getUser());
        User user = userRepository.findByUserPass(requestUserDTO.getUser(), requestUserDTO.getPassword());
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        if(user != null) {
            log.info("Validacion de Usuario Exitosa: User: {}", requestUserDTO.getUser());
            responseUserDTO.setId(user.getId());
            responseUserDTO.setName(user.getName());
            responseUserDTO.setEstado("Exitosa");
        }else{
            log.info("Validacion de Usuario Fallida: User: {}", requestUserDTO.getUser());
            responseUserDTO.setId(new BigDecimal(0));
            responseUserDTO.setEstado("Fallida");
        }

        return responseUserDTO;

    }

    public List<ResponseConveniosDTO> listConvenios (){
        log.info("Inicio de la recuperacion de la lista de Convenios");
        List<Convenios> conveniosList = (List<Convenios>) conveniosRepository.findAll();
        List<ResponseConveniosDTO> response = new ArrayList<>();
        for (Convenios convenio : conveniosList) {
            ResponseConveniosDTO item = new ResponseConveniosDTO();
            item.setId(convenio.getId());
            item.setName(convenio.getName());
            item.setCompensar(convenio.getCompensar());
            response.add(item);
        }
        log.info("Fin de la recuperacion de la lista de Convenios");
        return response;
    }

    public List<ResponseFormasPagoDTO> listFormasPago (){
        log.info("Inicio de la recuperacion de la lista de forma de pago");
        List<FormasPago> formasPagoList = (List<FormasPago>) formasPagoRepository.findAll();
        List<ResponseFormasPagoDTO> response = new ArrayList<>();
        for (FormasPago formasPago : formasPagoList) {
            ResponseFormasPagoDTO item = new ResponseFormasPagoDTO();
            item.setId(formasPago.getId());
            item.setDescription(formasPago.getDescription());
            response.add(item);
        }
        log.info("Fin de la recuperacion de la lista de forma de pago");
        return response;
    }

    public Mono<ResponseReferenciaFacturaDTO> referenciaFactura(RequestReferenciaFacturaDTO request){
        log.info("Inicio de la consulta de facturas: Referencia: {}", request.getReferenciaFactura());
        if(utilDataBase.validateConvenio(request.getConvenio())) {
            return getReferenciaFactura.getReferenciaFactura(request)
                    .flatMap(response -> {
                        ResponseReferenciaFacturaDTO responseReferenciaFacturaDTO = new ResponseReferenciaFacturaDTO();
                        responseReferenciaFacturaDTO.setReferenciaFactura(response.getReferenciaFactura().getReferenciaFactura());
                        responseReferenciaFacturaDTO.setTotalPagar(response.getTotalPagar());

                        if (response.getReferenciaFactura().getReferenciaFactura().equals("0")) {
                            responseReferenciaFacturaDTO.setCode("99");
                            responseReferenciaFacturaDTO.setMessage("Fallido");
                            log.info("Consulta de facturas: Referencia: {} - {}", request.getReferenciaFactura(), "Fallida");
                        } else {
                            responseReferenciaFacturaDTO.setCode("0");
                            responseReferenciaFacturaDTO.setMessage("Exitoso");
                            log.info("Consulta de facturas: Referencia: {} - {}", request.getReferenciaFactura(), "Exitoso");
                        }

                        return Mono.just(responseReferenciaFacturaDTO);
                    });
        }else{
            ResponseReferenciaFacturaDTO responseReferenciaFacturaDTO = new ResponseReferenciaFacturaDTO();
            responseReferenciaFacturaDTO.setCode("98");
            responseReferenciaFacturaDTO.setMessage("Convenio no registrado");
            log.info("Consulta de facturas: Convenio no registrado");
            return Mono.just(responseReferenciaFacturaDTO);
        }
    }

    public Mono<ResponseReferenciaFacturaDTO> pagoFactura(RequestPagoFacturaDTO request, OperationType type){
        log.info("Inicio Pago de facturas: Referencia: {}", request.getReferenciaFactura());
        if(utilDataBase.validateConvenio(request.getConvenio())) {
            return paymentsReferenciaFactura.paymentsReferenciaFactura(request, type)
                    .flatMap(response -> {
                        ResponseReferenciaFacturaDTO responseReferenciaFacturaDTO = new ResponseReferenciaFacturaDTO();
                        responseReferenciaFacturaDTO.setReferenciaFactura(response.getReferenciaFactura().getReferenciaFactura());
                        responseReferenciaFacturaDTO.setMessage(response.getMensaje());
                        responseReferenciaFacturaDTO.setTotalPagar(request.getValorFactura());

                        String estado;
                        if (response.getReferenciaFactura().getReferenciaFactura().equals("0")) {
                            responseReferenciaFacturaDTO.setCode("99");
                            estado = "Fallido";
                        } else {
                            responseReferenciaFacturaDTO.setCode("0");
                            estado = "Exitoso";
                        }
                        log.info("Pago de facturas: Referencia: {} - {}", request.getReferenciaFactura(), estado);
                        utilDataBase.updateTx(request, type, estado);
                        return Mono.just(responseReferenciaFacturaDTO);
                    });
        }else{
            ResponseReferenciaFacturaDTO responseReferenciaFacturaDTO = new ResponseReferenciaFacturaDTO();
            responseReferenciaFacturaDTO.setCode("98");
            responseReferenciaFacturaDTO.setMessage("Convenio no registrado");
            log.info("Pago de facturas: Convenio no registrado");
            return Mono.just(responseReferenciaFacturaDTO);
        }

    }

}
