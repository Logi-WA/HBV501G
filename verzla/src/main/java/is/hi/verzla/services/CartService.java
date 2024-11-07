package is.hi.verzla.services;

import is.hi.verzla.entities.CartItem;
import java.util.List;

/**
 * Service interface for handling operations related to the shopping cart.
 * Provides methods for retrieving, adding, updating, and removing cart items.
 */
public interface CartService {
  /**
   * Retrieves a list of cart items for a specific user.
   *
   * @param userId The ID of the user whose cart items are to be retrieved.
   * @return A list of {@link CartItem} objects associated with the user.
   */
  List<CartItem> getCartItemsByUserId(Long userId);

  /**
   * Adds a product to the user's cart.
   *
   * @param userId The ID of the user adding the product.
   * @param productId The ID of the product to be added to the cart.
   */
  void addProductToCart(Long userId, Long productId);

  /**
   * Updates the quantity of a specific cart item.
   *
   * @param cartItemId The ID of the cart item to be updated.
   * @param quantity The new quantity to set for the cart item.
   * @return The updated {@link CartItem}.
   */
  CartItem updateCartItemQuantity(Long cartItemId, int quantity);

  /**
   * Removes a product from the user's cart.
   *
   * @param userId The ID of the user whose cart item is to be removed.
   * @param productId The ID of the product to be removed from the cart.
   */
  void removeProductFromCart(Long userId, Long productId);
}
