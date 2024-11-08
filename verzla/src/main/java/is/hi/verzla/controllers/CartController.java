package is.hi.verzla.controllers;

import is.hi.verzla.entities.CartItem;
import is.hi.verzla.services.CartService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing shopping cart-related actions such as adding,
 * removing, updating, and viewing products in the user's cart.
 * <p>
 * This controller handles HTTP requests mapped to {@code /api/cart} and
 * interacts with the {@link CartService} to perform operations on
 * {@link CartItem} entities.
 * </p>
 *
 * <p>
 * Supported operations include:
 * <ul>
 *   <li>Fetching all cart items for the current user</li>
 *   <li>Adding a new product to the cart</li>
 *   <li>Updating the quantity of a cart item</li>
 *   <li>Removing a product from the cart</li>
 *   <li>Rendering the cart view page</li>
 * </ul>
 * </p>
 *
 * @see CartService
 * @see CartItem
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

  /**
   * Service layer for handling cart-related business logic.
   */
  @Autowired
  private CartService cartService;

  /**
   * Retrieves all cart items associated with the currently logged-in user.
   *
   * @param session The current HTTP session used to obtain the user ID.
   * @return A {@code List} of {@link CartItem} objects in the user's cart.
   */
  @GetMapping
  public List<CartItem> getCartItems(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    return cartService.getCartItemsByUserId(userId);
  }

  /**
   * Adds a specified product to the current user's shopping cart.
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
  public ResponseEntity<String> addToCart(
    @RequestBody ProductRequest productRequest,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("User must be logged in to add items to cart");
    }
    Long productId = productRequest.getProductId();
    try {
      cartService.addProductToCart(userId, productId);
      return ResponseEntity.ok("Product added to cart");
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error adding product to cart");
    }
  }

  /**
   * Updates the quantity of a specific cart item.
   *
   * @param id       The ID of the cart item to update.
   * @param quantity The new quantity to set for the cart item.
   * @return The updated {@link CartItem} object.
   *
   * @apiNote The quantity must be a positive integer. Additional validation can be
   *          implemented to enforce business rules.
   */
  @PatchMapping("/{cartItemId}")
  public ResponseEntity<?> updateCartItemQuantity(
    @PathVariable Long cartItemId,
    @RequestBody Map<String, Integer> requestBody,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("User must be logged in to update cart items");
    }

    int newQuantity = requestBody.get("quantity");
    if (newQuantity < 1) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("Quantity must be at least 1");
    }

    try {
      cartService.updateCartItemQuantity(cartItemId, newQuantity, userId);
      return ResponseEntity.ok("Cart item quantity updated");
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error updating cart item quantity");
    }
  }

  /**
   * Removes a specified product from the current user's shopping cart.
   *
   * @param productId The ID of the product to remove from the cart.
   * @param session   The current HTTP session used to obtain the user ID.
   * @return A {@link ResponseEntity} containing a success message if the operation
   *         is successful, or an error message if it fails.
   *
   * @apiNote The user must be logged in to perform this operation. If the user
   *          is not authenticated, an {@code UNAUTHORIZED} status is returned.
   */
  @DeleteMapping("/{cartItemId}")
  public ResponseEntity<String> removeFromCart(
    @PathVariable Long cartItemId,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("User must be logged in to remove items from cart");
    }
    try {
      cartService.removeCartItem(userId, cartItemId);
      return ResponseEntity.ok("Product removed from cart");
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error removing product from cart");
    }
  }

  /**
   * Renders the cart page for the current user.
   *
   * @param session The current HTTP session used to obtain the user ID.
   * @param model   The {@link Model} object used to pass data to the view.
   * @return The name of the view template for the cart page, or a redirect
   *         to the home page if the user is not logged in.
   *
   * @apiNote This method is intended for server-side rendering and may be used
   *          in conjunction with templating engines like Thymeleaf.
   */
  @GetMapping("/cart")
  public String viewCart(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      // User is not logged in; redirect to home page or login page
      return "redirect:/";
    }

    // Fetch cart items for the user
    List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
    model.addAttribute("cartItems", cartItems);

    return "cart";
  }

  /**
   * Inner static class representing the payload for adding a product to the
   * shopping cart.
   */
  public static class ProductRequest {

    /**
     * The ID of the product to be added to the cart.
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
