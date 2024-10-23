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

/**
 * REST controller for managing user-related operations.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * Retrieves a list of all users.
   *
   * @return A list of all User entities.
   */
  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id The ID of the user to retrieve.
   * @return The User entity with the specified ID.
   */
  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  /**
   * Creates a new user.
   *
   * @param user The User entity to be created.
   * @return A ResponseEntity indicating success or conflict if the email is already in use.
   */
  @PostMapping
  public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
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

  /**
   * Updates a user's details using a patch request.
   *
   * @param id The ID of the user to update.
   * @param userDetails The updated user details.
   * @return The updated User entity.
   */
  @PatchMapping("/{id}")
  public User patchUser(@PathVariable Long id, @RequestBody User userDetails) {
    return userService.updateUser(id, userDetails);
  }

  /**
   * Updates the password of a user by their ID.
   *
   * @param id The ID of the user.
   * @param newPassword The new password to set.
   * @return A confirmation message.
   */
  @PatchMapping("/{id}/password")
  public String updatePassword(
    @PathVariable Long id,
    @RequestBody String newPassword
  ) {
    userService.updatePassword(id, newPassword);
    return "Password updated successfully";
  }

  /**
   * Deletes a user by their ID.
   *
   * @param id The ID of the user to delete.
   * @return A confirmation message.
   */
  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return "User deleted with id " + id;
  }

  /**
   * Retrieves the currently logged-in user from the session.
   *
   * @param session The current HTTP session.
   * @return The User entity if logged in, or an unauthorized response if not.
   */
  @GetMapping("/me")
  public ResponseEntity<?> getCurrentUser(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId != null) {
      User user = userService.getUserById(userId);
      if (user != null) {
        return ResponseEntity.ok(user);
      }
    }
    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body("User not logged in");
  }

  /**
   * Updates the details of the currently logged-in user.
   *
   * @param userDetails The updated user details.
   * @param session The current HTTP session.
   * @return The updated User entity or an unauthorized response if not logged in.
   */
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
      session.setAttribute("userName", updatedUser.getName());
      session.setAttribute("userEmail", updatedUser.getEmail());
      return ResponseEntity.ok(updatedUser);
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error updating user");
    }
  }

  /**
   * Updates the password of the currently logged-in user.
   *
   * @param passwords A map containing the current and new passwords.
   * @param session The current HTTP session.
   * @return A confirmation message or an error response if the current password is incorrect or not logged in.
   */
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
