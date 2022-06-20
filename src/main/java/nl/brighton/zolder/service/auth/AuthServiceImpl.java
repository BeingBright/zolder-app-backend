package nl.brighton.zolder.service.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;
import nl.brighton.zolder.persistance.entity.TokenEntity;
import nl.brighton.zolder.service.exception.InvalidTokenException;
import nl.brighton.zolder.service.exception.InvalidUserException;
import nl.brighton.zolder.service.exception.UserNotFoundException;
import nl.brighton.zolder.service.user.UserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Setter(AccessLevel.NONE)
@Getter(AccessLevel.NONE)
public class AuthServiceImpl implements AuthService {

  private final TokenEntity tokenEntity;
  private final UserService userService;

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

  @Override
  public UserToken loginUser(User user) throws InvalidUserException {
    try {
      User userInDb = userService.getUser(user.getUsername());
      if (userInDb.equals(user) && userInDb.isActive()) {
        var token = tokenEntity.generateToken(userInDb);
        System.out.println(token);
        tokenEntity.addToken(token.getToken(), token);
        return token;
      }
      throw new InvalidUserException();
    } catch (UserNotFoundException e) {
      throw new InvalidUserException();
    }
  }


}
