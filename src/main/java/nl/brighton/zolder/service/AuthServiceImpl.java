package nl.brighton.zolder.service;

import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;
import nl.brighton.zolder.persistance.entity.TokenEntity;
import nl.brighton.zolder.service.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private TokenEntity tokenEntity;

  @Override
  public boolean isValid(String token) throws InvalidTokenException {

    if (tokenEntity.contains(token)) {
      return true;
    }
    throw new InvalidTokenException();
  }

  @Override
  public void removeToken(String token) {
    tokenEntity.removeToken(token);
  }

  @Override
  public UserToken generateToken(User user) {
    return tokenEntity.generateToken(user);
  }

  @Override
  public void addToken(String token, UserToken userToken) {
    tokenEntity.addToken(token, userToken);
  }

  @Autowired
  public void setTokenEntity(TokenEntity tokenEntity) {
    this.tokenEntity = tokenEntity;
  }
}
