package is.hi.verzla.services;

import is.hi.verzla.entities.Order;
import java.util.List;

public interface OrderService {
  Order getOrderById(Long id);
  List<Order> getOrdersByUserId(Long userId);
  Order createOrder(Order order);
  void deleteOrder(Long id);
}
