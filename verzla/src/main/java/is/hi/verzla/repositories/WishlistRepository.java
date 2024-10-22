package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.verzla.entities.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
  Wishlist findByUser_Id(Long userId);
}
