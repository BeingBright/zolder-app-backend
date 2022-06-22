package nl.brighton.zolder.persistance.entity;

import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;

public interface TokenEntity {

  boolean contains(String token);

  void addToken(String token, UserToken userToken);

  void removeToken(String token);

  UserToken generateToken(User user);

  UserToken getUserToken(String token);
}
