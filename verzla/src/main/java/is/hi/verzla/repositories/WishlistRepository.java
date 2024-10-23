package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.verzla.entities.Wishlist;

/**
 * Repository interface for performing CRUD operations on {@link Wishlist} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard data access methods, and includes
 * custom query methods tailored to the application's requirements.
 * </p>
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see is.hi.verzla.entities.Wishlist
 */
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

  /**
   * Finds a {@link Wishlist} by the ID of its associated user.
   *
   * @param userId The ID of the user whose wishlist is to be retrieved.
   * @return The {@link Wishlist} associated with the specified user ID, or {@code null}
   *         if no wishlist exists for the user.
   *
   * @throws IllegalArgumentException if {@code userId} is {@code null}.
   */
  Wishlist findByUser_Id(Long userId);
}
