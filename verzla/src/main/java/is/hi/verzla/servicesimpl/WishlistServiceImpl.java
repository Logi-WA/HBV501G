package is.hi.verzla.servicesimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import is.hi.verzla.entities.Product;
import is.hi.verzla.entities.User;
import is.hi.verzla.entities.Wishlist;
import is.hi.verzla.entities.WishlistItem;
import is.hi.verzla.repositories.ProductRepository;
import is.hi.verzla.repositories.UserRepository;
import is.hi.verzla.repositories.WishlistItemRepository;
import is.hi.verzla.repositories.WishlistRepository;
import is.hi.verzla.services.CartService;
import is.hi.verzla.services.WishlistService;

/**
 * Implementation of the {@link WishlistService} interface.
 * <p>
 * Provides methods for managing wishlists, including adding, retrieving, and
 * removing
 * products from a user's wishlist. This class interacts with various
 * repositories to
 * perform database operations related to wishlists and their items.
 * </p>
 *
 * @see WishlistService
 * @see is.hi.verzla.entities.Wishlist
 * @see is.hi.verzla.entities.WishlistItem
 */
@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {

  /**
   * Repository for accessing and manipulating {@link Wishlist} entities.
   */
  @Autowired
  private WishlistRepository wishlistRepository;

  /**
   * Repository for accessing and manipulating {@link WishlistItem} entities.
   */
  @Autowired
  private WishlistItemRepository wishlistItemRepository;

  /**
   * Repository for accessing and manipulating {@link Product} entities.
   */
  @Autowired
  private ProductRepository productRepository;

  /**
   * Repository for accessing and manipulating {@link User} entities.
   */
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CartService cartService;

  /**
   * Retrieves all wishlist items for a specific user.
   *
   * @param userId The ID of the user whose wishlist items are to be retrieved.
   * @return A {@code List} of {@link WishlistItem} objects belonging to the
   *         user's wishlist.
   *         Returns an empty list if the user has no wishlist items or if the
   *         wishlist does not exist.
   *
   * @throws IllegalArgumentException if {@code userId} is {@code null}.
   */
  @Override
  public List<WishlistItem> getWishlistByUserId(Long userId) {
    Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
    return wishlist != null ? wishlist.getWishlistItems() : new ArrayList<>();
  }

  /**
   * Adds a product to the user's wishlist.
   *
   * @param userId    The ID of the user.
   * @param productId The ID of the product to be added to the wishlist.
   *
   * @throws RuntimeException         if the user or product cannot be found.
   * @throws IllegalArgumentException if {@code userId} or {@code productId} is
   *                                  {@code null}.
   */
  @Override
  public void addProductToWishlist(Long userId, Long productId) {
    Product product = productRepository
        .findById(productId)
        .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

    Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
    if (wishlist == null) {
      wishlist = new Wishlist();
      User user = userRepository
          .findById(userId)
          .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
      wishlist.setUser(user);
      wishlist = wishlistRepository.save(wishlist);
    }

    // Check if the product is already in the wishlist to prevent duplicates
    WishlistItem existingItem = wishlistItemRepository.findByWishlistAndProduct(
        wishlist,
        product);
    if (existingItem != null) {
      return;
    }

    WishlistItem newItem = new WishlistItem();
    newItem.setWishlist(wishlist);
    newItem.setProduct(product);
    wishlistItemRepository.save(newItem);
  }

  /**
   * Removes a product from the user's wishlist.
   *
   * @param userId    The ID of the user.
   * @param productId The ID of the product to be removed from the wishlist.
   *
   * @throws RuntimeException         if the user or product cannot be found.
   * @throws IllegalArgumentException if {@code userId} or {@code productId} is
   *                                  {@code null}.
   */
  @Override
  public void removeWishlistItem(Long userId, Long wishlistItemId) {
    WishlistItem item = wishlistItemRepository
        .findById(wishlistItemId)
        .orElseThrow(() -> new RuntimeException(
            "Wishlist item not found with id " + wishlistItemId));

    // Ensure that the item belongs to the user before deleting it
    if (!item.getWishlist().getUser().getId().equals(userId)) {
      throw new RuntimeException("Wishlist item does not belong to user");
    }

    wishlistItemRepository.delete(item);
  }

  @Override
  public void addAllToCart(Long userId) {
    Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
    if (wishlist == null) {
      throw new RuntimeException("Wishlist not found for user");
    }

    List<WishlistItem> wishlistItems = wishlistItemRepository.findByWishlist(wishlist);

    for (WishlistItem item : wishlistItems) {
      cartService.addProductToCart(userId, item.getProduct().getId());
    }

  }

  @Override
  public void clearWishlist(Long userId) {
    Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
    if (wishlist != null) {
      wishlistItemRepository.deleteAllByWishlist(wishlist);
    }
  }
}
