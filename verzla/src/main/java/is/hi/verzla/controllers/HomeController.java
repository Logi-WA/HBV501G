package is.hi.verzla.controllers;

import is.hi.verzla.entities.Product;
import is.hi.verzla.entities.Category;
import is.hi.verzla.services.ProductService;
import is.hi.verzla.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

  @Autowired
  private ProductService productService;

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/") // Main entry point for the application
  public String index(Model model) {
      // Fetch all products and categories
      List<Product> products = productService.getProducts(null);
      List<Category> categories = categoryService.getAllCategories();

      // Add them to the model to be used in the template
      model.addAttribute("products", products);
      model.addAttribute("categories", categories);
      return "index"; // Return the existing index.html layout
  }
}
