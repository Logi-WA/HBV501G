package is.hi.verzla.services;

import is.hi.verzla.entities.WishlistItem;
import java.util.List;

public interface WishlistService {
  List<WishlistItem> getWishlistByUserId(Long userId);
  void addProductToWishlist(Long userId, Long productId);
  void removeProductFromWishlist(Long userId, Long productId);
}
