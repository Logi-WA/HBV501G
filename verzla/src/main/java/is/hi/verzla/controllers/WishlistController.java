package is.hi.verzla.controllers;

import is.hi.verzla.entities.WishlistItem;
import is.hi.verzla.services.WishlistService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

  @Autowired
  private WishlistService wishlistService;

  // Get products in the wishlist
  @GetMapping
  public List<WishlistItem> getWishlist(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    return wishlistService.getWishlistByUserId(userId);
  }

  // Add product to the wishlist
  @PostMapping
  public String addToWishlist(
    @RequestBody Long productId,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");
    wishlistService.addProductToWishlist(userId, productId);
    return "Product added to wishlist";
  }

  // Remove product from the wishlist
  @DeleteMapping
  public String removeFromWishlist(
    @RequestBody Long productId,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");
    wishlistService.removeProductFromWishlist(userId, productId);
    return "Product removed from wishlist";
  }
}
