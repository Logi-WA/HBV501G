package is.hi.verzla.servicesimpl;

import is.hi.verzla.entities.User;
import is.hi.verzla.repositories.UserRepository;
import is.hi.verzla.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User getUserById(Long id) {
    return userRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("User not found with id " + id));
  }

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

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

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public User updatePassword(Long id, String newPassword) {
    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    user.setPassword(newPassword);
    return userRepository.save(user);
  }

  @Override
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
