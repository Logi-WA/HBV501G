package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.verzla.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
  Cart findByUser_Id(Long userId);
}
