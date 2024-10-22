package is.hi.verzla.repositories;

import is.hi.verzla.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
  Cart findByUser_Id(Long userId);
}
