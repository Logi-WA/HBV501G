package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import is.hi.verzla.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
