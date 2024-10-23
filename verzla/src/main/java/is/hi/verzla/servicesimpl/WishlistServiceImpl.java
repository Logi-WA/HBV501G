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
 * Implementation of the {@link WishlistService} interface.
 * <p>
 * Provides methods for managing wishlists, including adding, retrieving, and removing
 * products from a user's wishlist. This class interacts with various repositories to
 * perform database operations related to wishlists and their items.
 * </p>
 *
 * @see WishlistService
 * @see is.hi.verzla.entities.Wishlist
 * @see is.hi.verzla.entities.WishlistItem
 */
@Service
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

  /**
   * Retrieves all wishlist items for a specific user.
   *
   * @param userId The ID of the user whose wishlist items are to be retrieved.
   * @return A {@code List} of {@link WishlistItem} objects belonging to the user's wishlist.
   *         Returns an empty list if the user has no wishlist items or if the wishlist does not exist.
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
   * @throws RuntimeException if the user or product cannot be found.
   * @throws IllegalArgumentException if {@code userId} or {@code productId} is {@code null}.
   */
  @Override
  public void addProductToWishlist(Long userId, Long productId) {
    User user = userRepository
        .findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

    Product product = productRepository
        .findById(productId)
        .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

    Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
    if (wishlist == null) {
      wishlist = new Wishlist();
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
    newItem.setUser(user);
    wishlistItemRepository.save(newItem);
  }

  /**
   * Removes a product from the user's wishlist.
   *
   * @param userId    The ID of the user.
   * @param productId The ID of the product to be removed from the wishlist.
   *
   * @throws RuntimeException if the user or product cannot be found.
   * @throws IllegalArgumentException if {@code userId} or {@code productId} is {@code null}.
   */
  @Override
  public void removeProductFromWishlist(Long userId, Long productId) {
    User user = userRepository
        .findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

    Product product = productRepository
        .findById(productId)
        .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

    Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
    if (wishlist != null) {
      WishlistItem item = wishlistItemRepository.findByWishlistAndProduct(
          wishlist,
          product);
      if (item != null) {
        wishlistItemRepository.delete(item);
      }
    }
  }
}
