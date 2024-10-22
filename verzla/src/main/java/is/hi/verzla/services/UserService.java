package is.hi.verzla.services;

import is.hi.verzla.entities.User;
import java.util.List;

public interface UserService {
  List<User> getAllUsers();
  User getUserById(Long id);
  User createUser(User user);
  User updateUser(Long id, User userDetails);
  void deleteUser(Long id);
  User updatePassword(Long id, String newPassword);
}
