package is.hi.verzla.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import is.hi.verzla.entities.CartItem;
import is.hi.verzla.entities.Category;
import is.hi.verzla.entities.Product;
import is.hi.verzla.entities.User;
import is.hi.verzla.entities.WishlistItem;
import is.hi.verzla.services.CartService;
import is.hi.verzla.services.CategoryService;
import is.hi.verzla.services.ProductService;
import is.hi.verzla.services.UserService;
import is.hi.verzla.services.WishlistService;
import jakarta.servlet.http.HttpSession;

/**
 * Controller responsible for handling view-related actions such as displaying
 * the home page, account page, shopping cart, and wishlist.
 */
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

  /**
   * Handles requests to the home page.
   *
   * @param model Model to pass data to the view.
   * @return The name of the view for the home page ("index").
   */
  @GetMapping("/")
  public String index(Model model) {
    List<Product> products = productService.getProducts(null);
    List<Category> categories = categoryService.getAllCategories();

    model.addAttribute("products", products);
    model.addAttribute("categories", categories);
    return "index";
  }

  /**
   * Handles requests to the account management page.
   *
   * @param session The current HTTP session to verify user login.
   * @param model Model to pass data to the view.
   * @return The name of the view for the account page ("account") or redirects
   *         to the home page if the user is not logged in.
   */
  @GetMapping("/account")
  public String accountPage(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return "redirect:/";
    }

    User user = userService.getUserById(userId);
    model.addAttribute("user", user);

    return "account";
  }

  /**
   * Handles requests to view the shopping cart.
   *
   * @param session The current HTTP session to verify user login.
   * @param model Model to pass data to the view.
   * @return The name of the view for the shopping cart ("cart") or redirects to
   *         the home page if the user is not logged in.
   */
  @GetMapping("/cart")
  public String viewCart(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return "redirect:/";
    }

    List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
    model.addAttribute("cartItems", cartItems);

    return "cart";
  }

  /**
   * Handles requests to view the wishlist.
   *
   * @param session The current HTTP session to verify user login.
   * @param model Model to pass data to the view.
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

  @GetMapping("/admin")
  public String adminPage(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return "redirect:/";
    }

    List<Product> products = productService.getProducts(null);
    model.addAttribute("products", products);
    List<User> users = userService.getAllUsers();
    model.addAttribute("users", users);

    return "admin";
  }
}
