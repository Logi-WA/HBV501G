package is.hi.verzla.repositories;

import is.hi.verzla.entities.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on {@link Product} entities.
 * Provides methods to interact with the product data in the database.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

  /**
   * Finds all products associated with a specific category by the category name.
   *
   * @param categoryName The name of the category whose products are to be fetched.
   * @return A list of {@link Product} entities belonging to the specified category.
   */
  List<Product> findByCategories_Name(String categoryName);
}
