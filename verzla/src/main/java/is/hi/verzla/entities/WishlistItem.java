package is.hi.verzla.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * Represents an item within a wishlist, linking a product to a specific wishlist.
 */
@Entity
@Table(name = "wishlist_items")
public class WishlistItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Product cannot be null")
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @NotNull(message = "Wishlist cannot be null")
  @ManyToOne
  @JoinColumn(name = "wishlist_id")
  private Wishlist wishlist;

  @NotNull(message = "User cannot be null")
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Default constructor for JPA.
   */
  public WishlistItem() {}

  /**
   * Gets the ID of the wishlist item.
   *
   * @return the ID of the wishlist item
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the wishlist item.
   *
   * @param id the ID to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the product associated with this wishlist item.
   *
   * @return the product associated with this wishlist item
   */
  public Product getProduct() {
    return product;
  }

  /**
   * Sets the product for this wishlist item.
   *
   * @param product the product to set
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * Gets the wishlist associated with this item.
   *
   * @return the wishlist associated with this item
   */
  public Wishlist getWishlist() {
    return wishlist;
  }

  /**
   * Sets the wishlist for this item.
   *
   * @param wishlist the wishlist to set
   */
  public void setWishlist(Wishlist wishlist) {
    this.wishlist = wishlist;
  }
}
