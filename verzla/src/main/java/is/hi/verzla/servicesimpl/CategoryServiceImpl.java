package is.hi.verzla.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import is.hi.verzla.entities.Category;
import is.hi.verzla.repositories.CategoryRepository;
import is.hi.verzla.services.CategoryService;

/**
 * Implementation of the {@link CategoryService} interface. Provides methods for managing
 * categories, including retrieving all available categories.
 */
@Service
@Transactional(readOnly = true) // REMOVE IF methods are added that need to write to the database
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  /**
   * Retrieves a list of all categories.
   *
   * @return A list of {@link Category} objects.
   */
  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }
}
