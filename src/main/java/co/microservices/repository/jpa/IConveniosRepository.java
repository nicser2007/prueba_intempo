package co.microservices.repository.jpa;

import co.microservices.domain.entity.Convenios;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface IConveniosRepository extends Serializable, CrudRepository<Convenios, Integer> {
}
