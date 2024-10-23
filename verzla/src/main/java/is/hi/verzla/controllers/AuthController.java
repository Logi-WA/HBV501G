package is.hi.verzla.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import is.hi.verzla.dto.LoginRequest;
import is.hi.verzla.entities.User;
import is.hi.verzla.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;

/**
 * Controller for handling authentication-related actions such as login and logout.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserRepository userRepository;

  /**
   * Handles user login by verifying credentials and starting a session if successful.
   *
   * @param loginRequest The login request containing username (email) and password.
   * @param session The HTTP session to store user information.
   * @return ResponseEntity with success message if login is successful, or an error message if login fails.
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestBody LoginRequest loginRequest,
      HttpSession session) {
    // Fetch user by email
    User user = userRepository.findByEmail(loginRequest.getUsername());
    if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
      // Check passwords
      if (user.getPassword().equals(loginRequest.getPassword())) {
        // Passwords match
        // Store user info in session
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        session.setAttribute("userEmail", user.getEmail());
        return ResponseEntity.ok("Login successful, session started.");
      } else {
        // Passwords don't match
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body("Invalid credentials.");
      }
    } else {
      // User not found
      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("Invalid credentials.");
    }
  }

  /**
   * Handles user logout by invalidating the current session.
   *
   * @param session The HTTP session to be invalidated.
   * @return ResponseEntity with success message indicating the logout was successful.
   */
  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpSession session) {
    // Invalidate session
    session.invalidate();
    return ResponseEntity.ok("Logout successful.");
  }
}
