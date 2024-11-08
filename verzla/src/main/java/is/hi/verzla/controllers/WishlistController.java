package is.hi.verzla.controllers;

import is.hi.verzla.entities.WishlistItem;
import is.hi.verzla.services.WishlistService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing wishlist-related actions such as adding,
 * removing, and viewing products in a user's wishlist.
 * <p>
 * This controller handles HTTP requests mapped to {@code /api/wishlist} and
 * interacts with the {@link WishlistService} to perform operations on
 * {@link WishlistItem} entities.
 * </p>
 *
 * <p>
 * Supported operations include:
 * <ul>
 *   <li>Fetching all wishlist items for the current user</li>
 *   <li>Adding a new product to the wishlist</li>
 *   <li>Removing a product from the wishlist</li>
 *   <li>Rendering the wishlist view page</li>
 * </ul>
 * </p>
 *
 * @see WishlistService
 * @see WishlistItem
 */
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

  /**
   * Service layer for handling wishlist-related business logic.
   */
  @Autowired
  private WishlistService wishlistService;

  /**
   * Retrieves all wishlist items associated with the currently logged-in user.
   *
   * @param session The current HTTP session used to obtain the user ID.
   * @return A {@code List} of {@link WishlistItem} objects belonging to the user.
   */
  @GetMapping
  public List<WishlistItem> getWishlist(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    return wishlistService.getWishlistByUserId(userId);
  }

  /**
   * Adds a specified product to the current user's wishlist.
   *
   * @param productRequest The request payload containing the ID of the product to add.
   * @param session        The current HTTP session used to obtain the user ID.
   * @return A {@link ResponseEntity} containing a success message if the operation
   *         is successful, or an error message if it fails.
   *
   * @apiNote The user must be logged in to perform this operation. If the user
   *          is not authenticated, an {@code UNAUTHORIZED} status is returned.
   */
  @PostMapping
  public ResponseEntity<String> addToWishlist(
    @RequestBody ProductRequest productRequest,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("User must be logged in to add items to wishlist");
    }
    Long productId = productRequest.getProductId();
    try {
      wishlistService.addProductToWishlist(userId, productId);
      return ResponseEntity.ok("Product added to wishlist");
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error adding product to wishlist");
    }
  }

  /**
   * Removes a specified product from the current user's wishlist.
   *
   * @param productId The ID of the product to remove from the wishlist.
   * @param session   The current HTTP session used to obtain the user ID.
   * @return A {@code String} message indicating the result of the removal operation.
   *
   * @apiNote The user must be logged in to perform this operation. If the user
   *          is not authenticated, the removal will silently fail or could be
   *          handled differently based on implementation.
   */
  @DeleteMapping("/{wishlistItemId}")
  public ResponseEntity<String> removeFromWishlist(
    @PathVariable Long wishlistItemId,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("You must be logged in to remove items from the wishlist");
    }
    try {
      wishlistService.removeWishlistItem(userId, wishlistItemId);
      return ResponseEntity.ok("Product removed from wishlist");
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error removing product from wishlist");
    }
  }

  /**
   * Renders the wishlist page for the current user.
   *
   * @param session The current HTTP session used to obtain the user ID.
   * @param model   The {@link Model} object used to pass data to the view.
   * @return The name of the view template for the wishlist page, or a redirect
   *         to the home page if the user is not logged in.
   *
   * @apiNote This method is intended for server-side rendering and may be used
   *          in conjunction with templating engines like Thymeleaf.
   */
  @GetMapping("/wishlist")
  public String viewWishlist(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return "redirect:/";
    }

    // Fetch wishlist items for the user
    List<WishlistItem> wishlistItems = wishlistService.getWishlistByUserId(
      userId
    );
    model.addAttribute("wishlistItems", wishlistItems);

    return "wishlist";
  }

  /**
   * Inner static class representing the payload for adding a product to the
   * wishlist.
   */
  public static class ProductRequest {

    /**
     * The ID of the product to be added to the wishlist.
     */
    private Long productId;

    /**
     * Retrieves the product ID from the request.
     *
     * @return The ID of the product to add.
     */
    public Long getProductId() {
      return productId;
    }

    /**
     * Sets the product ID for the request.
     *
     * @param productId The ID of the product to add.
     */
    public void setProductId(Long productId) {
      this.productId = productId;
    }
  }
}
