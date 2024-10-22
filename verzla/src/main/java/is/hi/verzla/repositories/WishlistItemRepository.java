package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.verzla.entities.Product;
import is.hi.verzla.entities.Wishlist;
import is.hi.verzla.entities.WishlistItem;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
  WishlistItem findByWishlistAndProduct(Wishlist wishlist, Product product);
}
