package is.hi.verzla.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import is.hi.verzla.entities.CartItem;
import is.hi.verzla.services.CartService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  // Get items in the shopping cart
  /**
   * @param session
   * @return
   */
  @GetMapping
  public List<CartItem> getCartItems(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    return cartService.getCartItemsByUserId(userId);
  }

  // Add item to the shopping cart
  /**
   * @param productRequest
   * @param session
   * @return
   */
  @PostMapping
  public String addToCart(@RequestBody ProductRequest productRequest, HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return "User must be logged in to add items to cart";
    }
    Long productId = productRequest.getProductId();
    cartService.addProductToCart(userId, productId);
    return "Product added to cart";
  }


  // Update quantity of an item in the cart
  /**
   * @param id
   * @param quantity
   * @return
   */
  @PatchMapping("/{id}")
  public CartItem updateCartItem(
      @PathVariable Long id,
      @RequestBody int quantity) {
    return cartService.updateCartItemQuantity(id, quantity);
  }

  // Remove product from the cart
  /**
   * @param productId
   * @param session
   * @return
   */
  @DeleteMapping
  public String removeFromCart(
      @RequestBody Long productId,
      HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    cartService.removeProductFromCart(userId, productId);
    return "Product removed from cart";
  }

  /**
   * @param session
   * @param model
   * @return
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