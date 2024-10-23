package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import is.hi.verzla.entities.Wishlist;

/**
 * Repository interface for performing CRUD operations on {@link Wishlist} entities.
 * Provides methods to interact with wishlists in the database.
 */
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

  /**
   * Finds a {@link Wishlist} by the ID of its associated user.
   *
   * @param userId The ID of the user whose wishlist is to be retrieved.
   * @return The {@link Wishlist} associated with the specified user ID, or null if none is found.
   */
  Wishlist findByUser_Id(Long userId);
}
