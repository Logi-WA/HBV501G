package is.hi.verzla.servicesimpl;

import is.hi.verzla.entities.Category;
import is.hi.verzla.repositories.CategoryRepository;
import is.hi.verzla.services.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
