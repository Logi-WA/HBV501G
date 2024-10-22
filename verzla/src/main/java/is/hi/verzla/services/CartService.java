package is.hi.verzla.services;

import is.hi.verzla.entities.CartItem;
import java.util.List;

public interface CartService {
  List<CartItem> getCartItemsByUserId(Long userId);
  void addProductToCart(Long userId, Long productId);
  CartItem updateCartItemQuantity(Long cartItemId, int quantity);
}