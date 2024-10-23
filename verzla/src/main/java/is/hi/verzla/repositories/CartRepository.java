package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import is.hi.verzla.entities.Cart;

/**
 * Repository interface for performing CRUD operations on {@link Cart} entities.
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

  /**
   * Finds a {@link Cart} by the associated user's ID.
   *
   * @param userId the ID of the user whose cart is to be retrieved
   * @return the {@link Cart} associated with the specified user ID, or {@code null} if not found
   */
  Cart findByUser_Id(Long userId);
}
