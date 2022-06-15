package nl.brighton.zolder.dto;

import java.util.Objects;

public class UserToken {

  private String token;
  private String user;
  private String userType;

  public UserToken() {
  }

  public UserToken(String token, String user, String userType) {
    this.token = token;
    this.user = user;
    this.userType = userType;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserToken userToken = (UserToken) o;
    return token.equals(userToken.token) && user.equals(userToken.user) && userType.equals(
        userToken.userType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, user, userType);
  }
}
