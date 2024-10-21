package is.hi.verzla.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import is.hi.verzla.entities.Order;
import is.hi.verzla.repositories.OrderRepository;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // Get order details by ID
    @GetMapping("/{id}")
    public Order getOrderDetails(@PathVariable Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }
}
