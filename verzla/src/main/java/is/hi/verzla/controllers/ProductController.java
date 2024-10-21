package is.hi.verzla.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import is.hi.verzla.entities.Product;
import is.hi.verzla.repositories.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Get all products, with optional filtering by category
    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) String category) {
        if (category != null) {
            return productRepository.findByCategories_Name(category);
        }
        return productRepository.findAll();
    }

    // Get product by id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }
}
