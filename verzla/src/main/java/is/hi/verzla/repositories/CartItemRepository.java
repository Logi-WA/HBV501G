package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.verzla.entities.Cart;
import is.hi.verzla.entities.CartItem;
import is.hi.verzla.entities.Product;

/**
 * Repository interface for performing CRUD operations on {@link CartItem}
 * entities.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

  /**
   * Finds a {@link CartItem} by the associated cart and product.
   *
   * @param cart    the cart to search within
   * @param product the product to search for
   * @return the found {@link CartItem}, or {@code null} if no matching item is
   *         found
   */
  CartItem findByCartAndProduct(Cart cart, Product product);

  void deleteAllByCart(Cart cart);
}
