package is.hi.verzla.services;

import java.util.List;
import is.hi.verzla.entities.Product;

public interface ProductService {
    List<Product> getProducts(String category);
    Product getProductById(Long id);
}
