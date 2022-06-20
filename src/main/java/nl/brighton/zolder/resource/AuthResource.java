package nl.brighton.zolder.resource;


import lombok.RequiredArgsConstructor;
import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;
import nl.brighton.zolder.service.auth.AuthService;
import nl.brighton.zolder.service.exception.InvalidTokenException;
import nl.brighton.zolder.service.exception.InvalidUserException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "auth")
public class AuthResource {

  private final AuthService authService;

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserToken> loginUser(@RequestBody User user)
      throws InvalidUserException {
    return ResponseEntity.ok(authService.loginUser(user));
  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> logoutUser(@RequestHeader String authorization)
      throws InvalidTokenException {
    if (authService.isValid(authorization)) {
      authService.removeToken(authorization);
      return ResponseEntity.ok("Logged Out");
    }
    throw new InvalidTokenException();

  }

}
