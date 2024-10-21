package is.hi.verzla.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import is.hi.verzla.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategories_Name(String categoryName);
}
