package is.hi.verzla.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String index() {
    return "index"; // finds src/main/resources/templates/index.html
  }
}
