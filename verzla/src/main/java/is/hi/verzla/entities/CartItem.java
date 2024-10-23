package is.hi.verzla.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Represents an individual item in a shopping cart, associated with a product.
 */
@Entity
@Table(name = "cart_items")
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Product cannot be null")
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @NotNull(message = "Cart cannot be null")
  @ManyToOne
  @JoinColumn(name = "cart_id")
  private Cart cart;

  @Positive(message = "Quantity must be positive")
  private int quantity;

  /**
   * Default constructor.
   */
  public CartItem() {}

  /**
   * Gets the ID of this cart item.
   *
   * @return the ID of this cart item.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of this cart item.
   *
   * @param id the ID to set.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the product associated with this cart item.
   *
   * @return the product associated with this cart item.
   */
  public Product getProduct() {
    return product;
  }

  /**
   * Sets the product associated with this cart item.
   *
   * @param product the product to set.
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * Gets the cart associated with this cart item.
   *
   * @return the cart associated with this cart item.
   */
  public Cart getCart() {
    return cart;
  }

  /**
   * Sets the cart associated with this cart item.
   *
   * @param cart the cart to set.
   */
  public void setCart(Cart cart) {
    this.cart = cart;
  }

  /**
   * Gets the quantity of the product in this cart item.
   *
   * @return the quantity of the product.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity of the product in this cart item.
   *
   * @param quantity the quantity to set.
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
