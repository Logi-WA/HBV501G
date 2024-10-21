package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.verzla.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
