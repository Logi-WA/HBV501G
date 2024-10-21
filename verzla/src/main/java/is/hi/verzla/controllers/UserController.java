package is.hi.verzla.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import is.hi.verzla.entities.User;
import is.hi.verzla.repositories.UserRepository;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  // Get all users
  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  // Get user by id
  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
    return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
  }

  // Create user
  @PostMapping
  public User createUser(@RequestBody User user) {
    return userRepository.save(user);
  }

  // Patch a user
  @PatchMapping("/{id}")
  public User patchUser(@PathVariable Long id, @RequestBody User userDetails) {
    User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));

    if (userDetails.getName() != null) {
      user.setName(userDetails.getName());
    }
    if (userDetails.getEmail() != null) {
      user.setEmail(userDetails.getEmail());
    }

    return userRepository.save(user);
  }

  // Delete user
  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
    return "User deleted with id " + id;
  }

  @PostConstruct
  public void initDatabase() {
    if (userRepository.count() == 0) {
      userRepository.save(new User("Alice", "alice@example.com"));
      userRepository.save(new User("Bob", "bob@example.com"));
    }
  }
}
