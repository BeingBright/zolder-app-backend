package nl.brighton.zolder.resource;


import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;
import nl.brighton.zolder.persistance.UserRepository;
import nl.brighton.zolder.service.AuthService;
import nl.brighton.zolder.service.exception.InvalidTokenException;
import nl.brighton.zolder.service.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
public class AuthResource {

  private UserRepository repository;

  private AuthService authService;

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserToken> loginUser(@RequestBody User user) throws InvalidUserException {
    var userInRepo = repository.getByUsername(user.getUsername());

    if (user.equals(userInRepo)) {
      var token = authService.generateToken(userInRepo);
      authService.addToken(token.getToken(), token);
      return ResponseEntity.ok(token);
    }

    throw new InvalidUserException("Invalid user credentials ");

  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> logoutUser(@RequestHeader String token)
      throws InvalidTokenException {
    if (authService.isValid(token)) {
      authService.removeToken(token);
      return ResponseEntity.ok("Logged Out");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");

  }

  @Autowired
  public void setRepository(UserRepository repository) {
    this.repository = repository;
  }

  @Autowired
  public void setAuthService(AuthService authService) {
    this.authService = authService;
  }
}
