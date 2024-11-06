package is.hi.verzla.services;

import is.hi.verzla.entities.Product;
import java.util.List;

/**
 * Service interface for managing products.
 * Provides methods to retrieve, create, and get product details.
 */
public interface ProductService {

    /**
     * Retrieves a list of products, optionally filtered by category.
     *
     * @param category The name of the category to filter by (optional).
     *                 If null, all products are retrieved.
     * @return A list of {@link Product} objects.
     */
    List<Product> getProducts(String category);

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The {@link Product} object with the specified ID.
     */
    Product getProductById(Long id);

    /**
     * Creates a new product.
     *
     * @param product The {@link Product} object to be created.
     * @return The created {@link Product} object.
     */
    Product createProduct(Product product);

    /**
     * Updates the name of a specific product by its ID.
     *
     * @param id The ID of the product to update.
     * @param newName The new name for the product.
     * @return The updated {@link Product} object.
     */
    Product updateProductName(Long id, String newName);

    /**
     * Updates the description of a specific product by its ID.
     *
     * @param id The ID of the product to update.
     * @param newDescription The new description for the product.
     * @return The updated {@link Product} object.
     */
    Product updateProductDescription(Long id, String newDescription);

}