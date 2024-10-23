package is.hi.verzla.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an order placed by a user, containing details such as order date, status, and associated order items.
 */
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "User cannot be null")
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @NotNull(message = "Order date cannot be null")
  private Date orderDate;

  @NotEmpty(message = "Status cannot be empty")
  private String status;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<OrderItem> orderItems = new ArrayList<>();

  /**
   * Default constructor for JPA.
   */
  public Order() {}

  /**
   * Gets the ID of the order.
   *
   * @return the ID of the order.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the order.
   *
   * @param id the ID to set.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the user who placed the order.
   *
   * @return the user associated with the order.
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user who placed the order.
   *
   * @param user the user to associate with the order.
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets the date when the order was placed.
   *
   * @return the order date.
   */
  public Date getOrderDate() {
    return orderDate;
  }

  /**
   * Sets the date when the order was placed.
   *
   * @param orderDate the date to set.
   */
  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  /**
   * Gets the status of the order (e.g., "Pending", "Shipped", "Delivered").
   *
   * @return the status of the order.
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets the status of the order.
   *
   * @param status the status to set.
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Gets the list of items associated with the order.
   *
   * @return the list of order items.
   */
  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  /**
   * Sets the list of items associated with the order.
   * Automatically sets the reference to this order in each OrderItem.
   *
   * @param orderItems the list of order items to associate with this order.
   */
  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
    for (OrderItem item : orderItems) {
      item.setOrder(this);
    }
  }
}
