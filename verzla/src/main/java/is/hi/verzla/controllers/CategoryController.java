package is.hi.verzla.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import is.hi.verzla.entities.Category;

@RestController
@RequestMapping("/api")
public class CategoryController {

  @GetMapping("/categories")
  public ResponseEntity<List<Category>> getCategories() {
    List<Category> categories = new ArrayList<>();
    categories.add(new Category("Books"));
    categories.add(new Category("Sweaters"));
    categories.add(new Category("Jeans"));
    categories.add(new Category("Jewelry"));
    categories.add(new Category("Shirts"));
    categories.add(new Category("Shoes"));

    return ResponseEntity.ok(categories);
  }
}
