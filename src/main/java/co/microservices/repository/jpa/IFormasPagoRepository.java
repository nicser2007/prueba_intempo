package co.microservices.repository.jpa;

import co.microservices.domain.entity.FormasPago;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface IFormasPagoRepository extends Serializable, CrudRepository<FormasPago, Integer> {
}
