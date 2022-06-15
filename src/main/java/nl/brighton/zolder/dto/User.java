package nl.brighton.zolder.dto;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@CompoundIndex(name = "username-index", def = "{'username':1}", unique = true)
@Document(collection = "user")
public class User {

  @Id
  private String id;

  private String username;
  private String password;
  private String type;

  public User() {
  }

  public User(String username, String password, String type) {
    this.username = username;
    this.password = password;
    this.type = type;
  }

  public User(String id, String username, String password, String type) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return username.equals(user.username) && password.equals(user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }

  @Override
  public String toString() {
    return "UserDTO{" +
        "username='" + username + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}

