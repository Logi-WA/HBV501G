package is.hi.verzla.entities;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a shopping cart associated with a specific user.
 * Each cart can contain multiple cart items, which are linked to specific products.
 */
@Entity
@Table(name = "carts")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "User cannot be null")
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<CartItem> cartItems = new ArrayList<>();

  /**
   * Default constructor for creating a new Cart instance.
   */
  public Cart() {
  }

  /**
   * Gets the ID of the cart.
   *
   * @return The cart ID.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the cart.
   *
   * @param id The new cart ID.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the list of items in the cart.
   *
   * @return A list of CartItem objects.
   */
  public List<CartItem> getCartItems() {
    return cartItems;
  }

  /**
   * Sets the list of items in the cart.
   *
   * @param cartItems The new list of CartItem objects.
   */
  public void setCartItems(List<CartItem> cartItems) {
    this.cartItems = cartItems;
  }

  /**
   * Gets the user associated with this cart.
   *
   * @return The User object.
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user associated with this cart.
   *
   * @param user The new User object.
   */
  public void setUser(User user) {
    this.user = user;
  }
}
