package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import is.hi.verzla.entities.Product;
import is.hi.verzla.entities.Wishlist;
import is.hi.verzla.entities.WishlistItem;

/**
 * Repository interface for performing CRUD operations on {@link WishlistItem} entities.
 * Provides methods to interact with wishlist items in the database.
 */
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

  /**
   * Finds a specific {@link WishlistItem} by its associated {@link Wishlist} and {@link Product}.
   *
   * @param wishlist The wishlist to which the item belongs.
   * @param product The product associated with the wishlist item.
   * @return The {@link WishlistItem} that matches the specified wishlist and product, or null if none is found.
   */
  WishlistItem findByWishlistAndProduct(Wishlist wishlist, Product product);
}
