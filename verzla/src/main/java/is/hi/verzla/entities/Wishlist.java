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
 * Represents a wishlist associated with a user, containing multiple wishlist items.
 */
@Entity
@Table(name = "wishlists")
public class Wishlist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "User cannot be null")
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<WishlistItem> wishlistItems = new ArrayList<>();

  /**
   * Default constructor for JPA.
   */
  public Wishlist() {}

  /**
   * Gets the ID of the wishlist.
   *
   * @return the ID of the wishlist
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the wishlist.
   *
   * @param id the ID to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the user associated with the wishlist.
   *
   * @return the user associated with the wishlist
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user associated with the wishlist.
   *
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets the list of items in the wishlist.
   *
   * @return the list of wishlist items
   */
  public List<WishlistItem> getWishlistItems() {
    return wishlistItems;
  }

  /**
   * Sets the list of items in the wishlist.
   *
   * @param wishlistItems the list of wishlist items to set
   */
  public void setWishlistItems(List<WishlistItem> wishlistItems) {
    this.wishlistItems = wishlistItems;
  }
}
