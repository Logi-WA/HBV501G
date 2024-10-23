package is.hi.verzla.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import is.hi.verzla.entities.Product;
import is.hi.verzla.repositories.ProductRepository;
import is.hi.verzla.services.ProductService;

/**
 * Implementation of the {@link ProductService} interface. Provides methods for
 * managing products, including retrieving, filtering, and creating products.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  /**
   * Retrieves a list of products, optionally filtered by category.
   *
   * @param category The name of the category to filter products by, or {@code null} to retrieve all products.
   * @return A list of {@link Product} objects, filtered by the specified category if provided.
   */
  @Override
  public List<Product> getProducts(String category) {
    if (category != null) {
      return productRepository.findByCategories_Name(category);
    }
    return productRepository.findAll();
  }

  /**
   * Retrieves a product by its ID.
   *
   * @param id The ID of the product to retrieve.
   * @return The {@link Product} with the specified ID.
   * @throws RuntimeException if the product with the specified ID is not found.
   */
  @Override
  public Product getProductById(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
  }

  /**
   * Creates a new product.
   *
   * @param product The {@link Product} object to be created.
   * @return The created {@link Product} object.
   */
  @Override
  public Product createProduct(Product product) {
    return productRepository.save(product); // Save new product with all its attributes
  }
}
