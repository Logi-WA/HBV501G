package is.hi.verzla.services;

import is.hi.verzla.entities.WishlistItem;
import java.util.List;

/**
 * Service interface for managing wishlists.
 * Provides methods to add, retrieve, and remove products from a user's wishlist.
 */
public interface WishlistService {
  /**
   * Retrieves the list of items in a user's wishlist.
   *
   * @param userId The ID of the user whose wishlist items are to be retrieved.
   * @return A list of {@link WishlistItem} objects in the user's wishlist.
   */
  List<WishlistItem> getWishlistByUserId(Long userId);

  /**
   * Adds a product to a user's wishlist.
   *
   * @param userId The ID of the user.
   * @param productId The ID of the product to be added to the wishlist.
   */
  void addProductToWishlist(Long userId, Long productId);

  /**
   * Removes a product from a user's wishlist.
   *
   * @param userId The ID of the user.
   * @param productId The ID of the product to be removed from the wishlist.
   */
  void removeProductFromWishlist(Long userId, Long productId);
}
