package is.hi.verzla.servicesimpl;

import is.hi.verzla.entities.Category;
import is.hi.verzla.repositories.CategoryRepository;
import is.hi.verzla.services.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link CategoryService} interface. Provides methods for managing
 * categories, including retrieving all available categories.
 */
@Service
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
