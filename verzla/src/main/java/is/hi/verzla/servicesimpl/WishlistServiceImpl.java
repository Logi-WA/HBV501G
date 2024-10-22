package is.hi.verzla.servicesimpl;

import is.hi.verzla.entities.Product;
import is.hi.verzla.entities.User;
import is.hi.verzla.entities.WishlistItem;
import is.hi.verzla.repositories.ProductRepository;
import is.hi.verzla.repositories.UserRepository;
import is.hi.verzla.repositories.WishlistRepository;
import is.hi.verzla.services.WishlistService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {

  @Autowired
  private WishlistRepository wishlistRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<WishlistItem> getWishlistByUserId(Long userId) {
    return wishlistRepository.findByUser_Id(userId);
  }

  @Override
  public void addProductToWishlist(Long userId, Long productId) {
    Product product = productRepository
      .findById(productId)
      .orElseThrow(() ->
        new RuntimeException("Product not found with id " + productId)
      );

    // Fetch the User entity
    User user = userRepository
      .findById(userId)
      .orElseThrow(() ->
        new RuntimeException("User not found with id " + userId)
      );

    // Check if the product is already in the wishlist
    WishlistItem existingItem = wishlistRepository.findByUserAndProduct(
      user,
      product
    );
    if (existingItem == null) {
      WishlistItem newItem = new WishlistItem();
      newItem.setProduct(product);
      newItem.setUser(user);
      wishlistRepository.save(newItem);
    }
  }

  @Override
  public void removeProductFromWishlist(Long userId, Long productId) {
    User user = userRepository
      .findById(userId)
      .orElseThrow(() ->
        new RuntimeException("User not found with id " + userId)
      );

    Product product = productRepository
      .findById(productId)
      .orElseThrow(() ->
        new RuntimeException("Product not found with id " + productId)
      );

    WishlistItem item = wishlistRepository.findByUserAndProduct(user, product);
    if (item != null) {
      wishlistRepository.delete(item);
    }
  }
}
