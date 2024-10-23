package is.hi.verzla.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Represents an item within an order, containing details about the product, quantity, and price.
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Order cannot be null")
  @ManyToOne
  @JoinColumn(name = "order_id")
  @JsonBackReference
  private Order order;

  @NotNull(message = "Product cannot be null")
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Positive(message = "Quantity must be positive")
  private int quantity;

  @PositiveOrZero(message = "Price must be zero or positive")
  private double price;

  /**
   * Gets the ID of the order item.
   *
   * @return the ID of the order item.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the order item.
   *
   * @param id the ID to set.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the order associated with this item.
   *
   * @return the associated order.
   */
  public Order getOrder() {
    return order;
  }

  /**
   * Sets the order associated with this item.
   *
   * @param order the order to associate with this item.
   */
  public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * Gets the product associated with this item.
   *
   * @return the associated product.
   */
  public Product getProduct() {
    return product;
  }

  /**
   * Sets the product associated with this item.
   *
   * @param product the product to associate with this item.
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * Gets the quantity of the product in the order.
   *
   * @return the quantity of the product.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity of the product in the order.
   *
   * @param quantity the quantity to set.
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Gets the price of the product in the order.
   *
   * @return the price of the product.
   */
  public double getPrice() {
    return price;
  }

  /**
   * Sets the price of the product in the order.
   *
   * @param price the price to set.
   */
  public void setPrice(double price) {
    this.price = price;
  }
}
