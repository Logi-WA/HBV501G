package is.hi.verzla.repositories;

import is.hi.verzla.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on {@link User} entities.
 * Provides methods to interact with user data in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Finds a user by their email address.
   *
   * @param email The email address of the user to be found.
   * @return The {@link User} entity with the specified email, or null if no user is found.
   */
  User findByEmail(String email);
}
