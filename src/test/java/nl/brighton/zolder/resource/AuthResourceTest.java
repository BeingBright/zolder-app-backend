package nl.brighton.zolder.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;
import nl.brighton.zolder.dto.types.UserType;
import nl.brighton.zolder.service.auth.AuthService;
import nl.brighton.zolder.service.exception.InvalidTokenException;
import nl.brighton.zolder.service.exception.InvalidUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class AuthResourceTest {

  private AuthResource sut;
  private AuthService mockedAuthService;

  @BeforeEach
  void setUp() throws InvalidTokenException, InvalidUserException {
    mockedAuthService = mock(AuthService.class);

    when(mockedAuthService.isValid("Correct")).thenReturn(true);
    when(mockedAuthService.isValid("Incorrect")).thenThrow(InvalidTokenException.class);

    when(mockedAuthService.loginUser(any())).thenReturn(new UserToken());
    when(mockedAuthService.loginUser(new User("username", "password", UserType.ADMIN))).thenThrow(
        InvalidUserException.class);

    sut = new AuthResource(mockedAuthService);
  }

  @Test
  void loginUser() throws InvalidUserException {
    var result = sut.loginUser(new User());
    assertEquals(result.getStatusCode(), HttpStatus.OK);

    assertThrows(InvalidUserException.class,
        () -> sut.loginUser(new User("username", "password", UserType.ADMIN)));
  }

  @Test
  void logoutUser() throws InvalidTokenException {
    var resultCorrect = sut.logoutUser("Correct");
    assertEquals(resultCorrect.getStatusCode(), HttpStatus.OK);

    assertThrows(InvalidTokenException.class, () -> sut.logoutUser("Incorrect"));

  }
}