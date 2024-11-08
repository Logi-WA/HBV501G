package is.hi.verzla.repositories;

import is.hi.verzla.entities.Product;
import is.hi.verzla.entities.Wishlist;
import is.hi.verzla.entities.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on {@link WishlistItem} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard data access methods, and includes
 * custom query methods tailored to the application's requirements.
 * </p>
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see is.hi.verzla.entities.WishlistItem
 */
public interface WishlistItemRepository
  extends JpaRepository<WishlistItem, Long> {
  /**
   * Finds a {@link WishlistItem} by its associated {@link Wishlist} and {@link Product}.
   *
   * @param wishlist The {@link Wishlist} to search within.
   * @param product  The {@link Product} to find in the wishlist.
   * @return The {@link WishlistItem} matching the given wishlist and product, or {@code null}
   *         if no such item exists.
   *
   * @throws IllegalArgumentException if {@code wishlist} or {@code product} is {@code null}.
   */
  WishlistItem findByWishlistAndProduct(Wishlist wishlist, Product product);
}
