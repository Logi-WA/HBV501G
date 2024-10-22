package is.hi.verzla.servicesimpl;

import is.hi.verzla.entities.Order;
import is.hi.verzla.repositories.OrderRepository;
import is.hi.verzla.services.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public Order getOrderById(Long id) {
    return orderRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
  }

  @Override
  public List<Order> getOrdersByUserId(Long userId) {
    return orderRepository.findByUserId(userId);
  }

  @Override
  public Order createOrder(Order order) {
    return orderRepository.save(order);
  }

  @Override
  public void deleteOrder(Long id) {
    orderRepository.deleteById(id);
  }
}
