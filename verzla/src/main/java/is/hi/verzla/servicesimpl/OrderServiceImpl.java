package is.hi.verzla.servicesimpl;

import is.hi.verzla.entities.Order;
import is.hi.verzla.repositories.OrderRepository;
import is.hi.verzla.services.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link OrderService} interface. Provides methods for managing
 * orders, including retrieving, creating, and deleting orders.
 */
@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  /**
   * Retrieves an order by its ID.
   *
   * @param id The ID of the order to retrieve.
   * @return The {@link Order} with the specified ID.
   * @throws RuntimeException if the order with the specified ID is not found.
   */
  @Override
  public Order getOrderById(Long id) {
    return orderRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
  }

  /**
   * Retrieves all orders associated with a specific user ID.
   *
   * @param userId The ID of the user whose orders to retrieve.
   * @return A list of {@link Order} objects belonging to the specified user.
   */
  @Override
  public List<Order> getOrdersByUserId(Long userId) {
    return orderRepository.findByUserId(userId);
  }

  /**
   * Creates a new order.
   *
   * @param order The {@link Order} object to be created.
   * @return The created {@link Order} object.
   */
  @Override
  public Order createOrder(Order order) {
    return orderRepository.save(order);
  }

  /**
   * Deletes an order by its ID.
   *
   * @param id The ID of the order to delete.
   */
  @Override
  public void deleteOrder(Long id) {
    orderRepository.deleteById(id);
  }
}
