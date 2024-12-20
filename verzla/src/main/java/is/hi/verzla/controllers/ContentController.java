package is.hi.verzla.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import is.hi.verzla.entities.Category;
import is.hi.verzla.entities.Product;
import is.hi.verzla.services.CategoryService;
import is.hi.verzla.services.ProductService;

/**
 * Controller for managing content displayed on the homepage.
 */
@Controller
public class ContentController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * Retrieves all products and categories to be displayed on the homepage.
     * Adds them to the model for rendering on the frontend.
     *
     * @param model The model to which the list of products and categories will be added.
     * @return The name of the template to be rendered for the homepage content.
     */
    @GetMapping("/content")
    public String getHomePage(Model model) {
        List<Product> products = productService.getProducts(null); // Fetch all products
        List<Category> categories = categoryService.getAllCategories(); // Fetch all categories
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "content";
    }
}
