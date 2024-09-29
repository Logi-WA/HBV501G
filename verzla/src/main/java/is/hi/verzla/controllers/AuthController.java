package is.hi.verzla.controllers;

import is.hi.verzla.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

    // simple implementation for now, more complex logic will be added later
    if ("user".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
      return ResponseEntity.ok("Login successful, session started.");
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
    }
  }
}
