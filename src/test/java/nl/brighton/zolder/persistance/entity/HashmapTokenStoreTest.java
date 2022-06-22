package nl.brighton.zolder.persistance.entity;

import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.model.user.AuthToken;
import nl.brighton.zolder.model.user.UserRoleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashmapTokenStoreTest {

  private HashmapTokenStore sut;
  private IRandomTokenGenerator randomTokenGenerator;

  @BeforeEach
  void setUp() {
    sut = new HashmapTokenStore();

    sut.addToken("Token2", new AuthToken("Token2", "User2", UserRoleType.ADMIN));
    sut.addToken("Token1", new AuthToken("Token1", "User1", UserRoleType.ADMIN));

    randomTokenGenerator = () -> "RandomToken";
    sut.setRandomTokenGenerator(randomTokenGenerator);
  }

  @Test
  void contains() {
    Assertions.assertTrue(sut.contains("Token1"));
    Assertions.assertFalse(sut.contains("Not A Token"));
  }

  @Test
  void addToken() {
    sut.addToken("Token3", new AuthToken("Token3", "User3", UserRoleType.ADMIN));
    Assertions.assertTrue(sut.contains("Token3"));
  }

  @Test
  void removeToken() {
    sut.removeToken("Token2");
    Assertions.assertTrue(sut.contains("Token1"));
    Assertions.assertFalse(sut.contains("Token2"));
  }

  @Test
  void generateToken() {
    String expected = "RandomToken";
    var result = sut.generateToken(new User("id", "username", "password", true, UserRoleType.ADMIN));
    Assertions.assertEquals(expected, result.getToken());
  }

  @Test
  void getUserToken() {
    AuthToken expected = new AuthToken("Token2", "User2", UserRoleType.ADMIN);
    var result = sut.getUserToken("Token2");
    Assertions.assertEquals(expected, result);
  }
}