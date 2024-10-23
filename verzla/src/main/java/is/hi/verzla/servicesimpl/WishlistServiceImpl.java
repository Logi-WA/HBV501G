package is.hi.verzla.servicesimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.verzla.entities.Product;
import is.hi.verzla.entities.User;
import is.hi.verzla.entities.Wishlist;
import is.hi.verzla.entities.WishlistItem;
import is.hi.verzla.repositories.ProductRepository;
import is.hi.verzla.repositories.UserRepository;
import is.hi.verzla.repositories.WishlistItemRepository;
import is.hi.verzla.repositories.WishlistRepository;
import is.hi.verzla.services.WishlistService;

/**
 * Implementation of the {@link WishlistService} interface. Provides methods for managing wishlists,
 * including adding, retrieving, and removing products from a user's wishlist.
 */
@Service
public class WishlistServiceImpl implements WishlistService {

  @Autowired
  private WishlistRepository wishlistRepository;

  @Autowired
  private WishlistItemRepository wishlistItemRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  /**
   * Retrieves all wishlist items for a specific user.
   *
   * @param userId The ID of the user whose wishlist items are to be retrieved.
   * @return A list of {@link WishlistItem} objects belonging to the user's wishlist.
   */
  @Override
  public List<WishlistItem> getWishlistByUserId(Long userId) {
    Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
    return wishlist != null ? wishlist.getWishlistItems() : new ArrayList<>();
  }

  /**
   * Adds a product to the user's wishlist.
   *
   * @param userId The ID of the user.
   * @param productId The ID of the product to be added to the wishlist.
   * @throws RuntimeException if the user or product cannot be found.
   */
  @Override
  public void addProductToWishlist(Long userId, Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

    Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
    if (wishlist == null) {
      wishlist = new Wishlist();
      wishlist.setUser(user);
      wishlist = wishlistRepository.save(wishlist);
    }

    WishlistItem existingItem = wishlistItemRepository.findByWishlistAndProduct(wishlist, product);
    if (existingItem == null) {
      WishlistItem newItem = new WishlistItem();
      newItem.setProduct(product);
      newItem.setWishlist(wishlist);
      wishlistItemRepository.save(newItem);
    }
  }

  /**
   * Removes a product from the user's wishlist.
   *
   * @param userId The ID of the user.
   * @param productId The ID of the product to be removed from the wishlist.
   * @throws RuntimeException if the user or product cannot be found.
   */
  @Override
  public void removeProductFromWishlist(Long userId, Long productId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

    Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
    if (wishlist != null) {
      WishlistItem item = wishlistItemRepository.findByWishlistAndProduct(wishlist, product);
      if (item != null) {
        wishlistItemRepository.delete(item);
      }
    }
  }
}
