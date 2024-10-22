package is.hi.verzla.services;

import java.util.List;

import is.hi.verzla.entities.WishlistItem;

public interface WishlistService {
  List<WishlistItem> getWishlistByUserId(Long userId);

  void addProductToWishlist(Long userId, Long productId);

  void removeProductFromWishlist(Long userId, Long productId);
}
