package is.hi.verzla.services;

import is.hi.verzla.entities.User;
import java.util.List;

/**
 * Service interface for managing users.
 * Provides methods to create, retrieve, update, and delete users, as well as manage passwords.
 */
public interface UserService {

  /**
   * Retrieves a list of all users.
   *
   * @return A list of {@link User} objects.
   */
  List<User> getAllUsers();

  /**
   * Retrieves a user by their ID.
   *
   * @param id The ID of the user to retrieve.
   * @return The {@link User} object with the specified ID.
   */
  User getUserById(Long id);

  /**
   * Creates a new user.
   *
   * @param user The {@link User} object to be created.
   * @return The created {@link User} object.
   */
  User createUser(User user);

  /**
   * Updates an existing user's details.
   *
   * @param id The ID of the user to update.
   * @param userDetails The new details for the user.
   * @return The updated {@link User} object.
   */
  User updateUser(Long id, User userDetails);

  /**
   * Deletes a user by their ID.
   *
   * @param id The ID of the user to delete.
   */
  void deleteUser(Long id);

  /**
   * Updates the password for a specific user.
   *
   * @param id The ID of the user whose password will be updated.
   * @param newPassword The new password for the user.
   * @return The updated {@link User} object.
   */
  User updatePassword(Long id, String newPassword);

  /**
   * Retrieves a user by their email address.
   *
   * @param email The email address of the user to retrieve.
   * @return The {@link User} object with the specified email.
   */
  User getUserByEmail(String email);
}
