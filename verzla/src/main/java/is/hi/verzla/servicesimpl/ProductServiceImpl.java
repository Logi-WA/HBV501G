package is.hi.verzla.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import is.hi.verzla.entities.Product;
import is.hi.verzla.repositories.ProductRepository;
import is.hi.verzla.services.ProductService;

/**
 * Implementation of the {@link ProductService} interface. Provides methods for
 * managing products, including retrieving, updating, and creating products.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves a list of products, optionally filtered by category.
     *
     * @param category The name of the category to filter products by, or {@code null} to retrieve all products.
     * @return A list of {@link Product} objects, filtered by the specified category if provided.
     */
    @Override
    public List<Product> getProducts(String category) {
        if (category != null) {
            return productRepository.findByCategories_Name(category);
        }
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The {@link Product} with the specified ID.
     * @throws RuntimeException if the product with the specified ID is not found.
     */
    @Override
    public Product getProductById(Long id) {
        return productRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    /**
     * Creates a new product.
     *
     * @param product The {@link Product} object to be created.
     * @return The created {@link Product} object.
     */
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product); // Save new product with all its attributes
    }

    /**
     * Updates both the name and description of a specific product by its ID.
     *
     * @param id             The ID of the product to update.
     * @param newName        The new name for the product.
     * @param newDescription The new description for the product.
     * @return The updated {@link Product} object.
     */
    @Override
    public Product updateProduct(Long id, String newName, String newDescription) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        if (newName != null) {
            product.setName(newName);
        }
        if (newDescription != null) {
            product.setDescription(newDescription);
        }
        return productRepository.save(product);
    }

    /**
     * Updates the name of a specific product by its ID.
     *
     * @param id      The ID of the product to update.
     * @param newName The new name for the product.
     * @return The updated {@link Product} object.
     */
    @Override
    public Product updateProductName(Long id, String newName) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        if (newName != null) {
            product.setName(newName);
        }
        return productRepository.save(product);
    }

    /**
     * Updates the description of a specific product by its ID.
     *
     * @param id             The ID of the product to update.
     * @param newDescription The new description for the product.
     * @return The updated {@link Product} object.
     */
    @Override
    public Product updateProductDescription(Long id, String newDescription) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        if (newDescription != null) {
            product.setDescription(newDescription);
        }
        return productRepository.save(product);
    }
}
