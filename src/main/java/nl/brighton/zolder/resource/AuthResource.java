package nl.brighton.zolder.resource;


import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;
import nl.brighton.zolder.persistance.UserRepository;
import nl.brighton.zolder.persistance.entity.TokenEntity;
import nl.brighton.zolder.service.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "auth")
public class AuthResource {

  private UserRepository repository;

  private TokenEntity tokenEntity;

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserToken> addUser(@RequestBody User user) throws InvalidUserException {
    var userInRepo = repository.getByUsername(user.getUsername());

    if (user.equals(userInRepo)) {
      var token = tokenEntity.generateToken(userInRepo);
      tokenEntity.addToken(token.getToken(), token);
      return ResponseEntity.ok(token);
    }

    throw new InvalidUserException("Unknown user");

  }

  @ResponseBody
  @RequestMapping(path = "/{token}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserToken> getUserToken(@PathVariable String token) {

    return ResponseEntity.ok(tokenEntity.getUserToken(token));
  }

  @Autowired
  public void setRepository(UserRepository repository) {
    this.repository = repository;
  }

  @Autowired
  public void setTokenEntity(TokenEntity tokenEntity) {
    this.tokenEntity = tokenEntity;
  }
}
