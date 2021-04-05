package co.microservices.repository.jpa;

import co.microservices.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

public interface IUserRepository extends Serializable, CrudRepository<User, Integer> {

    @Query(value = "SELECT u FROM User u WHERE u.user = :user AND u.password = :password ")
    User findByUserPass(@Param("user") String user,
                        @Param("password") String password);

}
