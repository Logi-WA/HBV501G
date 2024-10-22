package is.hi.verzla.servicesimpl;

import is.hi.verzla.entities.Product;
import is.hi.verzla.repositories.ProductRepository;
import is.hi.verzla.services.ProductService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public List<Product> getProducts(String category) {
    if (category != null) {
      return productRepository.findByCategories_Name(category);
    }
    return productRepository.findAll();
  }

  @Override
  public Product getProductById(Long id) {
    return productRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
  }

  @Override
  public Product createProduct(Product product) {
    return productRepository.save(product);  // Save new product with all its attributes
  }
}
