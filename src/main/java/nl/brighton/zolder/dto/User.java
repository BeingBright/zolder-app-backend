package nl.brighton.zolder.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.brighton.zolder.dto.types.UserType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id", "type", "isActive"})
@Document(collection = "user")
@CompoundIndex(name = "username-index", def = "{'username':1}", unique = true)
public class User {

  @Id
  private String id;
  private String username;
  private String password;
  private UserType type;
  private boolean isActive = false;

  public User(String username, String password, UserType type) {
    this.id = null;
    this.username = username;
    this.type = type;
    this.password = password;
  }

  public boolean hasSameID(User user) {
    return this.id.equals(user.id);
  }
}

