package nl.brighton.zolder.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;
import nl.brighton.zolder.dto.types.UserType;
import nl.brighton.zolder.persistance.entity.TokenEntity;
import nl.brighton.zolder.service.exception.InvalidTokenException;
import nl.brighton.zolder.service.exception.InvalidUserException;
import nl.brighton.zolder.service.exception.UserNotFoundException;
import nl.brighton.zolder.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthServiceImplTest {

  private AuthServiceImpl sut;
  private TokenEntity mockedTokenEntity;
  private UserService mockedUserService;

  @BeforeEach
  void setUp() throws UserNotFoundException {
    mockedTokenEntity = mock(TokenEntity.class);

    when(mockedTokenEntity.contains("Correct Token")).thenReturn(true);
    when(mockedTokenEntity.contains("False Token")).thenReturn(false);
    when(mockedTokenEntity.generateToken(any())).thenReturn(new UserToken());

    mockedUserService = mock(UserService.class);
    when(mockedUserService.getUser("Correct")).thenReturn(
        new User("", "Correct", "Password", UserType.ADMIN, true));
    when(mockedUserService.getUser("Incorrect")).thenReturn(
        new User("", "Incorrect", "NO Pass", UserType.ADMIN, true));

    sut = new AuthServiceImpl(mockedTokenEntity, mockedUserService);
  }

  @Test
  void isValid() throws InvalidTokenException {
    assertTrue(sut.isValid("Correct Token"));
    assertThrows(InvalidTokenException.class, () -> assertTrue(sut.isValid("False Token")));
  }

  @Test
  void removeToken() {
    sut.removeToken("anyString");
    verify(mockedTokenEntity).removeToken(anyString());
  }

  @Test
  void generateToken() {
    sut.generateToken(new User());
    verify(mockedTokenEntity).generateToken(any());
  }

  @Test
  void addToken() {
    sut.addToken("A Token", new UserToken());
    verify(mockedTokenEntity).addToken(anyString(), any());
  }

  @Test
  void loginUser() throws InvalidUserException {
    var result = sut.loginUser(new User("", "Correct", "Password", UserType.ADMIN, false));
    verify(mockedTokenEntity).generateToken(any());
    verify(mockedTokenEntity).addToken(any(), any());
    assertEquals(new UserToken(), result);

    assertThrows(InvalidUserException.class,
        () -> sut.loginUser(new User("", "Incorrect", "Password", UserType.ADMIN, false)));
  }
}
