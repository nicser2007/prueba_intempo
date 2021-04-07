package co.microservices.util;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

import co.microservices.domain.OperationType;
import co.microservices.domain.entity.Convenios;
import co.microservices.domain.entity.Transacciones;
import co.microservices.domain.request.RequestPagoFacturaDTO;
import co.microservices.repository.jpa.IConveniosRepository;
import co.microservices.repository.jpa.ITransaccionesRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class UtilDataBase {

    private final IConveniosRepository conveniosRepository;
    private final ITransaccionesRepository transaccionesRepository;


    public UtilDataBase(IConveniosRepository conveniosRepository, ITransaccionesRepository transaccionesRepository) {
        this.conveniosRepository = conveniosRepository;
        this.transaccionesRepository = transaccionesRepository;
    }

    public boolean validateConvenio(String name){
        boolean response = true;

        if(conveniosRepository.findByName(name) == null){
            response = false;
        }
        return response;
    }

    public void updateTx(RequestPagoFacturaDTO request, OperationType type, String estado){
        Transacciones tx = new Transacciones();

        Long maxId = getMax() + 1;
        tx.setId(BigDecimal.valueOf(maxId));
        tx.setConvenios(getConvenio(request.getConvenio()));
        tx.setReferenciaFactura(request.getReferenciaFactura());
        tx.setEstado(estado);
        tx.setValorFactura(request.getValorFactura());
        tx.setTipo(type.getType());
        tx.setFecha(new Date());

        transaccionesRepository.save(tx);
    }

    public Convenios getConvenio(String name){
        return conveniosRepository.findByName(name);
    }

    public long getMax(){
        return transaccionesRepository.count();
    }


}
