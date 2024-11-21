package is.hi.verzla.controllers;

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

import is.hi.verzla.entities.User;
import is.hi.verzla.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * REST controller for managing user-related operations such as creating,
 * retrieving, updating, and deleting user accounts.
 * <p>
 * This controller handles HTTP requests mapped to {@code /api/users} and
 * interacts with the {@link UserService} to perform operations on
 * {@link User} entities.
 * </p>
 *
 * <p>
 * Supported operations include:
 * <ul>
 * <li>Retrieving all users</li>
 * <li>Retrieving a user by ID</li>
 * <li>Creating a new user</li>
 * <li>Updating user details</li>
 * <li>Updating user passwords</li>
 * <li>Deleting a user</li>
 * <li>Managing the currently logged-in user's information</li>
 * </ul>
 * </p>
 *
 * @see UserService
 * @see User
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

  /**
   * Service layer for handling user-related business logic.
   */
  @Autowired
  private UserService userService;

  /**
   * Retrieves a list of all users.
   *
   * @return A {@code List} of all {@link User} entities.
   *
   * @apiNote This operation is typically restricted to administrative users.
   */
  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  /**
   * Retrieves a specific user by their unique ID.
   *
   * @param id The ID of the user to retrieve.
   * @return The {@link User} entity with the specified ID, or an error response
   *         if the user is not found.
   *
   * @apiNote Ensure that only authorized users can access user details.
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    User user = userService.getUserById(id);
    if (user != null) {
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("User not found with id " + id);
    }
  }

  /**
   * Creates a new user account.
   *
   * @param user The {@link User} entity to be created. Must be valid according to
   *             the validation constraints defined in the {@code User} class.
   * @return A {@link ResponseEntity} indicating success with the created user,
   *         or a conflict response if the email is already in use.
   *
   * @apiNote Passwords should be securely hashed before being stored.
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
   * Updates a user's details using a PATCH request.
   *
   * @param id          The ID of the user to update.
   * @param userDetails A {@link User} object containing the updated user details.
   * @return A {@link ResponseEntity} containing the updated user, or an error
   *         response if the update fails.
   *
   * @apiNote Only certain fields should be updatable to maintain data integrity.
   */
  @PatchMapping("/{id}")
  public ResponseEntity<?> patchUser(@PathVariable Long id, @RequestBody User userDetails) {
    try {
      User updatedUser = userService.updateUser(id, userDetails);
      return ResponseEntity.ok(updatedUser);
    } catch (IllegalArgumentException e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error updating user");
    }
  }

  /**
   * Updates the password of a user by their ID.
   *
   * @param id          The ID of the user.
   * @param newPassword The new password to set.
   * @return A {@link ResponseEntity} containing a confirmation message, or an
   *         error response if the update fails.
   */
  @PatchMapping("/{id}/password")
  public ResponseEntity<String> updatePassword(
      @PathVariable Long id,
      @RequestBody String newPassword) {
    try {
      userService.updatePassword(id, newPassword);
      return ResponseEntity.ok("Password updated successfully");
    } catch (IllegalArgumentException e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error updating password");
    }
  }

  /**
   * Deletes a user by their unique ID.
   *
   * @param id The ID of the user to delete.
   * @return A {@link ResponseEntity} containing a confirmation message, or an
   *         error response if the deletion fails.
   *
   * @apiNote Deleting a user should also handle cascading deletions or data
   *          integrity.
   */
  @DeleteMapping("/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
    try {
      userService.deleteUser(userId);
      return ResponseEntity.ok("User deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error deleting user: " + e.getMessage());
    }
  }

  /**
   * Retrieves the currently logged-in user from the session.
   *
   * @param session The current HTTP session.
   * @return A {@link ResponseEntity} containing the {@link User} entity if logged
   *         in,
   *         or an unauthorized response if not.
   *
   * @apiNote This endpoint is useful for frontend applications to fetch user
   *          details.
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
   * @param session     The current HTTP session.
   * @return A {@link ResponseEntity} containing the updated {@link User} entity,
   *         or an unauthorized response if not logged in.
   *
   * @apiNote Only certain fields should be updatable to maintain data integrity.
   */
  @PatchMapping("/me")
  public ResponseEntity<?> updateCurrentUser(
      @RequestBody User userDetails,
      HttpSession session) {
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
    } catch (IllegalArgumentException e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error updating user");
    }
  }

  /**
   * Updates the password of the currently logged-in user.
   *
   * @param passwords A {@code Map} containing the current and new passwords.
   *                  Expected keys: {@code "currentPassword"} and
   *                  {@code "newPassword"}.
   * @param session   The current HTTP session.
   * @return A {@link ResponseEntity} containing a confirmation message or an
   *         error response if the current password is incorrect or not logged in.
   *
   * @apiNote Passwords should be securely hashed before being stored.
   */
  @PatchMapping("/me/password")
  public ResponseEntity<?> updateCurrentUserPassword(
      @RequestBody Map<String, String> passwords,
      HttpSession session) {
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
    } catch (IllegalArgumentException e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error updating password");
    }
  }

  /**
   * Inner static class representing the payload for adding a product to the
   * shopping cart.
   */
  public static class ProductRequest {
    /**
     * The ID of the product to be added to the cart.
     */
    private Long productId;

    /**
     * Retrieves the product ID from the request.
     *
     * @return The ID of the product to add.
     */
    public Long getProductId() {
      return productId;
    }

    /**
     * Sets the product ID for the request.
     *
     * @param productId The ID of the product to add.
     */
    public void setProductId(Long productId) {
      this.productId = productId;
    }
  }
}
