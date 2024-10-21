package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import is.hi.verzla.entities.Cart;
import is.hi.verzla.entities.CartItem;
import is.hi.verzla.entities.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProduct(Cart cart, Product product);
}
