package co.microservices.service;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

import co.microservices.domain.entity.Convenios;
import co.microservices.domain.entity.FormasPago;
import co.microservices.domain.entity.User;
import co.microservices.domain.request.RequestDTO;
import co.microservices.domain.response.ResponseConveniosDTO;
import co.microservices.domain.response.ResponseFormasPagoDTO;
import co.microservices.domain.response.ResponseUserDTO;
import co.microservices.repository.jpa.IConveniosRepository;
import co.microservices.repository.jpa.IFormasPagoRepository;
import co.microservices.repository.jpa.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PruebaIntempoService {

    private final IUserRepository userRepository;
    private final IConveniosRepository conveniosRepository;
    private final IFormasPagoRepository formasPagoRepository;

    public PruebaIntempoService(IUserRepository userRepository, IConveniosRepository conveniosRepository, IFormasPagoRepository formasPagoRepository) {
        this.userRepository = userRepository;
        this.conveniosRepository = conveniosRepository;
        this.formasPagoRepository = formasPagoRepository;
    }


    public ResponseUserDTO validateUser (RequestDTO requestDTO){

        User user = userRepository.findByUserPass(requestDTO.getUser(), requestDTO.getPassword());
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        if(user != null) {
            responseUserDTO.setId(user.getId());
            responseUserDTO.setName(user.getName());
        }else{
            responseUserDTO.setId(new BigDecimal(0));
        }

        return responseUserDTO;

    }

    public List<ResponseConveniosDTO> listConvenios (){
        List<Convenios> conveniosList = (List<Convenios>) conveniosRepository.findAll();
        List<ResponseConveniosDTO> response = new ArrayList<>();
        for (Convenios convenio : conveniosList) {
            ResponseConveniosDTO item = new ResponseConveniosDTO();
            item.setId(convenio.getId());
            item.setName(convenio.getName());
            item.setCompensar(convenio.getCompensar());
            response.add(item);
        }
        return response;
    }

    public List<ResponseFormasPagoDTO> listFormasPago (){
        List<FormasPago> formasPagoList = (List<FormasPago>) formasPagoRepository.findAll();
        List<ResponseFormasPagoDTO> response = new ArrayList<>();
        for (FormasPago formasPago : formasPagoList) {
            ResponseFormasPagoDTO item = new ResponseFormasPagoDTO();
            item.setId(formasPago.getId());
            item.setDescription(formasPago.getDescription());
            response.add(item);
        }
        return response;
    }
}
