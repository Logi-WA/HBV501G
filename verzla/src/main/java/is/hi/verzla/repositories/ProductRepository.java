package is.hi.verzla.repositories;

import is.hi.verzla.entities.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByCategories_Name(String categoryName);
}
