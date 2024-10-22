package is.hi.verzla.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import is.hi.verzla.entities.CartItem;
import is.hi.verzla.entities.Category;
import is.hi.verzla.entities.Product;
import is.hi.verzla.entities.WishlistItem;
import is.hi.verzla.services.CartService;
import is.hi.verzla.services.CategoryService;
import is.hi.verzla.services.ProductService;
import is.hi.verzla.services.UserService;
import is.hi.verzla.services.WishlistService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ViewController {

  @Autowired
  private ProductService productService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private UserService userService;

  @Autowired
  private CartService cartService;

  @Autowired
  private WishlistService wishlistService;

  // =========================================
  // Home Page
  // =========================================
  @GetMapping("/") // Main entry point for the application
  public String index(Model model) {
    // Fetch all products and categories
    List<Product> products = productService.getProducts(null);
    List<Category> categories = categoryService.getAllCategories();

    // Add them to the model to be used in the template
    model.addAttribute("products", products);
    model.addAttribute("categories", categories);
    return "index"; // Return the existing index.html layout
  }

  // =========================================
  // Account Management
  // =========================================
  @GetMapping("/account")
  public String accountPage(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      // User is not logged in; redirect to home page
      return "redirect:/";
    }

    // Pass user information to the view
    String userName = (String) session.getAttribute("userName");
    model.addAttribute("userName", userName);

    return "account"; // Returns the account.html template
  }

  // =========================================
  // Shopping Cart
  // =========================================
  @GetMapping("/cart")
  public String viewCart(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      // User is not logged in; redirect to home page
      return "redirect:/";
    }

    // Fetch cart items for the user
    List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
    model.addAttribute("cartItems", cartItems);

    return "cart"; // Returns the cart.html template
  }

  // =========================================
  // Wishlist
  // =========================================
  @GetMapping("/wishlist")
  public String viewWishlist(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      // User is not logged in; redirect to home page
      return "redirect:/";
    }

    // Fetch wishlist items for the user
    List<WishlistItem> wishlistItems = wishlistService.getWishlistByUserId(userId);
    model.addAttribute("wishlistItems", wishlistItems);

    return "wishlist"; // Returns the wishlist.html template
  }
}
