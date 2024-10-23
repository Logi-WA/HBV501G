package is.hi.verzla.controllers;

import is.hi.verzla.entities.Product;
import is.hi.verzla.services.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
