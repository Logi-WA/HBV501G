package is.hi.verzla.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a product in the e-commerce system, including its name, price,
 * description, and associated categories.
 */
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Product name cannot be empty")
  private String name;

  private double price;  // Price of the product
  private String imageUrl;  // URL to the product image
  private String description;  // Brief description of the product

  @ManyToMany
  @JoinTable(
    name = "product_categories",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private Set<Category> categories = new HashSet<>();

  /**
   * Gets the ID of the product.
   *
   * @return the ID of the product.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the product.
   *
   * @param id the ID to set.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the product.
   *
   * @return the name of the product.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the product.
   *
   * @param name the name to set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the price of the product.
   *
   * @return the price of the product.
   */
  public double getPrice() {
    return price;
  }

  /**
   * Sets the price of the product.
   *
   * @param price the price to set.
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Gets the URL of the product image.
   *
   * @return the URL of the product image.
   */
  public String getImageUrl() {
    return imageUrl;
  }

  /**
   * Sets the URL of the product image.
   *
   * @param imageUrl the image URL to set.
   */
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  /**
   * Gets the description of the product.
   *
   * @return the description of the product.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the product.
   *
   * @param description the description to set.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the categories associated with the product.
   *
   * @return the categories associated with the product.
   */
  public Set<Category> getCategories() {
    return categories;
  }

  /**
   * Sets the categories associated with the product.
   *
   * @param categories the categories to set.
   */
  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }
}
