package is.hi.verzla.services;

import java.util.List;
import is.hi.verzla.entities.CartItem;

public interface CartService {
    List<CartItem> getCartItemsByUserId(Long userId);
    void addProductToCart(Long userId, Long productId);
    CartItem updateCartItemQuantity(Long cartItemId, int quantity);
}
