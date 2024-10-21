package is.hi.verzla.servicesimpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.hi.verzla.entities.Cart;
import is.hi.verzla.entities.CartItem;
import is.hi.verzla.entities.Product;
import is.hi.verzla.repositories.CartRepository;
import is.hi.verzla.repositories.CartItemRepository;
import is.hi.verzla.repositories.ProductRepository;
import is.hi.verzla.services.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CartItem> getCartItemsByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        return cart != null ? cart.getCartItems() : new ArrayList<>();
    }

    @Override
    public void addProductToCart(Long userId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart = cartRepository.save(cart);
        }

        CartItem existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(1);
            cartItemRepository.save(newCartItem);
        }
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found with id " + cartItemId));
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }
}
