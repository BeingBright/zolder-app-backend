package nl.brighton.zolder.persistance.entity;

import java.util.HashMap;
import java.util.UUID;
import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;
import org.springframework.stereotype.Service;

@Service
public class HashmapTokenStore implements TokenEntity {

  private static HashMap<String, UserToken> tokens = new HashMap<>();
  private IRandomTokenGenerator randomTokenGenerator = UUID.randomUUID()::toString;


  @Override
  public boolean contains(String token) {
    return tokens.containsKey(token);
  }


  @Override
  public void addToken(String token, UserToken userToken) {
    tokens.putIfAbsent(userToken.getToken(), userToken);
  }

  @Override
  public void removeToken(String token) {
    tokens.remove(token);
  }

  @Override
  public UserToken generateToken(User user) {
    return new UserToken(
        randomTokenGenerator.generate(),
        user.getUsername(),
        user.getType()
    );
  }

  @Override
  public UserToken getUserToken(String token) {
    return tokens.get(token);
  }

  public void setRandomTokenGenerator(
      IRandomTokenGenerator randomTokenGenerator) {
    this.randomTokenGenerator = randomTokenGenerator;
  }
}
