package nl.brighton.zolder.persistance.entity;

import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.model.user.AuthToken;

public interface TokenEntity {

  boolean contains(String token);

  void addToken(String token, AuthToken authToken);

  void removeToken(String token);

  AuthToken generateToken(User user);

  AuthToken getUserToken(String token);
}
