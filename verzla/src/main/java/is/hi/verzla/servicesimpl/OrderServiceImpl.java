package is.hi.verzla.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.hi.verzla.entities.Order;
import is.hi.verzla.repositories.OrderRepository;
import is.hi.verzla.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }
}
