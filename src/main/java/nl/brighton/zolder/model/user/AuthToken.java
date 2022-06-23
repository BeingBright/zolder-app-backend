package nl.brighton.zolder.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = "auth-token")
@CompoundIndex(name = "token-index", def = "{'token':1}", unique = true)

public class AuthToken {

  private String token;
  private String username;
  private UserRoleType role;
  @JsonIgnore
  private Date expireDate;

  public AuthToken(String token, String username, UserRoleType role, long tokenValidity) {
    this.token = token;
    this.username = username;
    this.role = role;
    this.expireDate = new Date(System.currentTimeMillis() + tokenValidity * 1000);
  }

  public AuthToken(String token, String username, UserRoleType role) {
    this.token = token;
    this.username = username;
    this.role = role;
    this.expireDate = new Date(System.currentTimeMillis() + 3600 * 1000);
  }

}
