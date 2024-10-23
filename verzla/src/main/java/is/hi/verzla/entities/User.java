package is.hi.verzla.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 * Represents a user in the e-commerce system, including their name, email, and password.
 */
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Name cannot be empty")
  private String name;

  @Email(message = "Email should be valid")
  @NotEmpty(message = "Email cannot be empty")
  @Column(unique = true)
  private String email;

  @NotEmpty(message = "Password cannot be empty")
  private String password;

  /**
   * Default constructor for JPA.
   */
  public User() {}

  /**
   * Constructs a new User with the specified name, email, and password.
   *
   * @param name     the name of the user
   * @param email    the email of the user
   * @param password the password of the user
   */
  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  /**
   * Gets the ID of the user.
   *
   * @return the ID of the user
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the user.
   *
   * @param id the ID to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the user.
   *
   * @return the name of the user
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the user.
   *
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the email of the user.
   *
   * @return the email of the user
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the user.
   *
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the password of the user.
   *
   * @return the password of the user
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the user.
   *
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
