package is.hi.verzla.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping
    public List<CartItem> getCartItems(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return cartService.getCartItemsByUserId(userId);
    }

    // Add item to the shopping cart
    @PostMapping
    public String addToCart(@RequestBody Long productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        cartService.addProductToCart(userId, productId);
        return "Product added to cart";
    }

    // Update quantity of an item in the cart
    @PatchMapping("/{id}")
    public CartItem updateCartItem(@PathVariable Long id, @RequestBody int quantity) {
        return cartService.updateCartItemQuantity(id, quantity);
    }
}
