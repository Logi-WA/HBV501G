package is.hi.verzla.servicesimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.hi.verzla.entities.WishlistItem;
import is.hi.verzla.entities.Product;
import is.hi.verzla.repositories.WishlistRepository;
import is.hi.verzla.repositories.ProductRepository;
import is.hi.verzla.services.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<WishlistItem> getWishlistByUserId(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    @Override
    public void addProductToWishlist(Long userId, Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

        // Check if the product is already in the wishlist
        WishlistItem existingItem = wishlistRepository.findByUserIdAndProduct_Id(userId, productId);
        if (existingItem == null) {
            WishlistItem newItem = new WishlistItem(product, userId);
            wishlistRepository.save(newItem);
        }
    }

    @Override
    public void removeProductFromWishlist(Long userId, Long productId) {
        WishlistItem item = wishlistRepository.findByUserIdAndProduct_Id(userId, productId);
        if (item != null) {
            wishlistRepository.delete(item);
        }
    }
}
