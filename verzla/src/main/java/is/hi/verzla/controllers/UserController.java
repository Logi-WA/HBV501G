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
import is.hi.verzla.services.UserService;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Create user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Patch a user
    @PatchMapping("/{id}")
    public User patchUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    // Update password
    @PatchMapping("/{id}/password")
    public String updatePassword(@PathVariable Long id, @RequestBody String newPassword) {
        userService.updatePassword(id, newPassword);
        return "Password updated successfully";
    }

    // Delete user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted with id " + id;
    }

    // Initialize with sample data
    @PostConstruct
    public void initDatabase() {
        if (userService.getAllUsers().isEmpty()) {
            userService.createUser(new User("Alice", "alice@example.com"));
            userService.createUser(new User("Bob", "bob@example.com"));
        }
    }
}
