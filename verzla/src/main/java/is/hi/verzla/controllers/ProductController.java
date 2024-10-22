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

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  // Get all products, with optional filtering by category
  @GetMapping
  public List<Product> getProducts(
    @RequestParam(required = false) String category
  ) {
    return productService.getProducts(category);
  }

  // Get product by id
  @GetMapping("/{id}")
  public Product getProductById(@PathVariable Long id) {
    return productService.getProductById(id);
  }

  // Create a new product
  @PostMapping
  public Product createProduct(@RequestBody Product product) {
    return productService.createProduct(product);
  }
}
