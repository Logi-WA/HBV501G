package is.hi.verzla.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import is.hi.verzla.controllers.CartController.ProductRequest;
import is.hi.verzla.entities.WishlistItem;
import is.hi.verzla.services.WishlistService;
import jakarta.servlet.http.HttpSession;

/**
 * Controller for managing wishlist-related actions such as adding, removing,
 * and viewing products in the user's wishlist.
 */
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

  @Autowired
  private WishlistService wishlistService;

  /**
   * Fetches the products in the user's wishlist.
   *
   * @param session The current HTTP session to get user ID.
   * @return A list of WishlistItem objects.
   */
  @GetMapping
  public List<WishlistItem> getWishlist(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    return wishlistService.getWishlistByUserId(userId);
  }

  /**
   * Adds a product to the user's wishlist.
   *
   * @param productRequest Contains the product ID to add.
   * @param session The current HTTP session to get user ID.
   * @return A message indicating success or failure.
   */
  @PostMapping
  public String addToWishlist(@RequestBody ProductRequest productRequest, HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return "User must be logged in to add items to wishlist";
    }
    Long productId = productRequest.getProductId();
    wishlistService.addProductToWishlist(userId, productId);
    return "Product added to wishlist";
  }

  /**
   * Removes a product from the user's wishlist.
   *
   * @param productId The ID of the product to remove.
   * @param session The current HTTP session to get user ID.
   * @return A message indicating success of the removal.
   */
  @DeleteMapping
  public String removeFromWishlist(@RequestBody Long productId, HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    wishlistService.removeProductFromWishlist(userId, productId);
    return "Product removed from wishlist";
  }

  /**
   * Handles the view rendering for the wishlist page.
   *
   * @param session The current HTTP session to get user ID.
   * @param model The Model to pass data to the view.
   * @return The name of the view for the wishlist ("wishlist") or redirects to
   *         the home page if the user is not logged in.
   */
  @GetMapping("/wishlist")
  public String viewWishlist(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return "redirect:/";
    }

    List<WishlistItem> wishlistItems = wishlistService.getWishlistByUserId(userId);
    model.addAttribute("wishlistItems", wishlistItems);

    return "wishlist";
  }

  /**
   * Inner static class to represent the product request payload.
   */
  public static class ProductRequest {
    private Long productId;

    public Long getProductId() {
      return productId;
    }

    public void setProductId(Long productId) {
      this.productId = productId;
    }
  }
}
