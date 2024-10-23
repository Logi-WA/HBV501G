package is.hi.verzla.repositories;

import is.hi.verzla.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on {@link Category} entities.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
