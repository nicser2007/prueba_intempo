package co.microservices.repository.jpa;
/*
 * Copyright @2021. Todos los derechos reservados.
 *
 * @author Gomez, Gelver
 * @version 1, 2021
 * @since 1.0
 */

import co.microservices.domain.entity.FormasPago;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface IFormasPagoRepository extends Serializable, CrudRepository<FormasPago, Integer> {
}
