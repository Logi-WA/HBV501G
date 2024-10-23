package is.hi.verzla.controllers;

import is.hi.verzla.entities.User;
import is.hi.verzla.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
    // Check if email is already in use
    if (userService.getUserByEmail(user.getEmail()) != null) {
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body("Email already in use");
    }

    try {
      User newUser = userService.createUser(user);
      return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error creating user");
    }
  }

  // Patch a user
  @PatchMapping("/{id}")
  public User patchUser(@PathVariable Long id, @RequestBody User userDetails) {
    return userService.updateUser(id, userDetails);
  }

  // Update password
  @PatchMapping("/{id}/password")
  public String updatePassword(
    @PathVariable Long id,
    @RequestBody String newPassword
  ) {
    userService.updatePassword(id, newPassword);
    return "Password updated successfully";
  }

  // Delete user
  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return "User deleted with id " + id;
  }

  @GetMapping("/me")
  public ResponseEntity<?> getCurrentUser(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId != null) {
      User user = userService.getUserById(userId);
      if (user != null) {
        // Return user entity
        return ResponseEntity.ok(user);
      }
    }
    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body("User not logged in");
  }

  // Update user details
  @PatchMapping("/me")
  public ResponseEntity<?> updateCurrentUser(
    @RequestBody User userDetails,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("User not logged in");
    }

    try {
      User updatedUser = userService.updateUser(userId, userDetails);
      // Update session attributes if necessary
      session.setAttribute("userName", updatedUser.getName());
      session.setAttribute("userEmail", updatedUser.getEmail());
      return ResponseEntity.ok(updatedUser);
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error updating user");
    }
  }

  // Update password
  @PatchMapping("/me/password")
  public ResponseEntity<?> updateCurrentUserPassword(
    @RequestBody Map<String, String> passwords,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("User not logged in");
    }

    String currentPassword = passwords.get("currentPassword");
    String newPassword = passwords.get("newPassword");

    try {
      User user = userService.getUserById(userId);
      if (!user.getPassword().equals(currentPassword)) {
        return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body("Current password is incorrect");
      }

      userService.updatePassword(userId, newPassword);
      return ResponseEntity.ok("Password updated successfully");
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error updating password");
    }
  }
}
