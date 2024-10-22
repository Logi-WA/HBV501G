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

  @Override
  public List<WishlistItem> getWishlistByUserId(Long userId) {
    Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
    return wishlist != null ? wishlist.getWishlistItems() : new ArrayList<>();
  }

  @Override
  public void addProductToWishlist(Long userId, Long productId) {
    Product product = productRepository
        .findById(productId)
        .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

    // Fetch the User entity
    User user = userRepository
        .findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

    // Get or create the wishlist
    Wishlist wishlist = wishlistRepository.findByUser_Id(userId);
    if (wishlist == null) {
      wishlist = new Wishlist();
      wishlist.setUser(user);
      wishlist = wishlistRepository.save(wishlist);
    }

    // Check if the product is already in the wishlist
    WishlistItem existingItem = wishlistItemRepository.findByWishlistAndProduct(wishlist, product);
    if (existingItem == null) {
      WishlistItem newItem = new WishlistItem();
      newItem.setProduct(product);
      newItem.setWishlist(wishlist); // Set the wishlist
      wishlistItemRepository.save(newItem);
    }
  }

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
      WishlistItem item = wishlistItemRepository.findByWishlistAndProduct(wishlist, product);
      if (item != null) {
        wishlistItemRepository.delete(item);
      }
    }
  }
}
