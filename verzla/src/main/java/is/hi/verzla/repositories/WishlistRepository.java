package is.hi.verzla.repositories;

import is.hi.verzla.entities.Product;
import is.hi.verzla.entities.User;
import is.hi.verzla.entities.WishlistItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
  List<WishlistItem> findByUser(User user);
  List<WishlistItem> findByUser_Id(Long userId);
  WishlistItem findByUserAndProduct(User user, Product product);
}
