package is.hi.verzla.repositories;

import is.hi.verzla.entities.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on {@link Order} entities.
 * Provides methods to interact with the order data in the database.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

  /**
   * Finds all orders associated with a specific user by their user ID.
   *
   * @param userId The ID of the user whose orders are to be fetched.
   * @return A list of {@link Order} entities belonging to the specified user.
   */
  List<Order> findByUserId(Long userId);
}
