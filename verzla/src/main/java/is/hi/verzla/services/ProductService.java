package is.hi.verzla.services;

import is.hi.verzla.entities.Product;
import java.util.List;

public interface ProductService {
  List<Product> getProducts(String category);
  Product getProductById(Long id);
}
