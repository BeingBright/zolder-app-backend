package nl.brighton.zolder.persistance.entity;

import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashmapTokenStoreTest {

  private HashmapTokenStore sut;
  private IRandomTokenGenerator randomTokenGenerator;

  @BeforeEach
  void setUp() {
    sut = new HashmapTokenStore();

    sut.addToken("Token1", new UserToken("Token1", "User1", "Type1"));
    sut.addToken("Token2", new UserToken("Token2", "User2", "Type2"));

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
    sut.addToken("Token3", new UserToken("Token3", "User3", "Type3"));
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
    var result = sut.generateToken(new User("username", "password", "type"));
    Assertions.assertEquals(expected, result.getToken());
  }

  @Test
  void getUserToken() {
    UserToken expected = new UserToken("Token2", "User2", "Type2");
    var result = sut.getUserToken("Token2");
    Assertions.assertEquals(expected, result);
  }
}