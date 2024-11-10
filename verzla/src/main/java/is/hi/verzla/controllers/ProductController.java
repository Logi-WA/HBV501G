package is.hi.verzla.controllers;

import is.hi.verzla.entities.Product;
import is.hi.verzla.services.ProductService;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing products.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  /**
   * Retrieves a list of all products, with optional filtering by category.
   *
   * @param category Optional category filter to retrieve products by specific category.
   * @return A list of products, filtered by category if provided.
   */
  @GetMapping
  public List<Product> getProducts(
    @RequestParam(required = false) String category
  ) {
    return productService.getProducts(category);
  }

  /**
   * Retrieves a specific product by its ID.
   *
   * @param id The ID of the product to retrieve.
   * @return The Product object with the specified ID.
   */
  @GetMapping("/{id}")
  public Product getProductById(@PathVariable Long id) {
    return productService.getProductById(id);
  }

  /**
   * Creates a new product.
   *
   * @param product The Product object to be created.
   * @return The created Product object.
   */
  @PostMapping
  public Product createProduct(@RequestBody Product product) {
    return productService.createProduct(product);
  }

  /**
   * Updates the name of a specific product by its ID.
   *
   * @param id The ID of the product to update.
   * @param updates A map containing the new name.
   * @return Response entity with the updated product or error message.
   */
  @PatchMapping("/{id}/name")
  public ResponseEntity<?> updateProductName(
    @PathVariable Long id,
    @RequestBody Map<String, String> updates
  ) {
    try {
      String newName = updates.get("name");
      Product updatedProduct = productService.updateProductName(id, newName);
      return ResponseEntity.ok(updatedProduct);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Failed to update product name.");
    }
  }

  /**
   * Updates the description of a specific product by its ID.
   *
   * @param id The ID of the product to update.
   * @param updates A map containing the new description.
   * @return Response entity with the updated product or error message.
   */
  @PatchMapping("/{id}/description")
  public ResponseEntity<?> updateProductDescription(
    @PathVariable Long id,
    @RequestBody Map<String, String> updates
  ) {
    try {
      String newDescription = updates.get("description");
      Product updatedProduct = productService.updateProductDescription(id, newDescription);
      return ResponseEntity.ok(updatedProduct);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Failed to update product description.");
    }
  }

  // Inner classes for request bodies
  public static class UpdateNameRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
  }

  public static class UpdateDescriptionRequest {
      private String description;

      public String getDescription() {
          return description;
      }

      public void setDescription(String description) {
          this.description = description;
      }
  }
}