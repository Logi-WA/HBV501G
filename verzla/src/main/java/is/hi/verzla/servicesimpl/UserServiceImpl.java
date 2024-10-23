package is.hi.verzla.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import is.hi.verzla.entities.User;
import is.hi.verzla.repositories.UserRepository;
import is.hi.verzla.services.UserService;

/**
 * Implementation of the {@link UserService} interface. Provides methods for managing users, including
 * creating, updating, retrieving, and deleting user accounts.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  /**
   * Retrieves a list of all users.
   *
   * @return A list of {@link User} objects.
   */
  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id The ID of the user to retrieve.
   * @return The {@link User} with the specified ID.
   * @throws RuntimeException if the user with the specified ID is not found.
   */
  @Override
  public User getUserById(Long id) {
    return userRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("User not found with id " + id));
  }

  /**
   * Creates a new user.
   *
   * @param user The {@link User} object to be created.
   * @return The created {@link User} object.
   */
  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

  /**
   * Updates the details of an existing user.
   *
   * @param id The ID of the user to update.
   * @param userDetails The {@link User} object containing updated details.
   * @return The updated {@link User} object.
   * @throws RuntimeException if the user with the specified ID is not found or if the new email is already in use.
   */
  @Override
  public User updateUser(Long id, User userDetails) {
    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("User not found with id " + id));

    if (userDetails.getName() != null && !userDetails.getName().isEmpty()) {
      user.setName(userDetails.getName());
    }
    if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty()) {
      // Check if the new email is already in use by another user
      User existingUser = userRepository.findByEmail(userDetails.getEmail());
      if (existingUser != null && !existingUser.getId().equals(id)) {
        throw new RuntimeException("Email already in use");
      }
      user.setEmail(userDetails.getEmail());
    }

    return userRepository.save(user);
  }

  /**
   * Deletes a user by their ID.
   *
   * @param id The ID of the user to delete.
   */
  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  /**
   * Updates the password of an existing user.
   *
   * @param id The ID of the user whose password will be updated.
   * @param newPassword The new password to set.
   * @return The updated {@link User} object.
   * @throws RuntimeException if the user with the specified ID is not found.
   */
  @Override
  public User updatePassword(Long id, String newPassword) {
    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    user.setPassword(newPassword);
    return userRepository.save(user);
  }

  /**
   * Retrieves a user by their email.
   *
   * @param email The email of the user to retrieve.
   * @return The {@link User} object with the specified email, or {@code null} if not found.
   */
  @Override
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
