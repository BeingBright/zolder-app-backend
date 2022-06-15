package nl.brighton.zolder.resource;


import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;
import nl.brighton.zolder.persistance.UserRepository;
import nl.brighton.zolder.persistance.entity.TokenEntity;
import nl.brighton.zolder.service.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

  @Autowired
  public void setRepository(UserRepository repository) {
    this.repository = repository;
  }

  @Autowired
  public void setTokenEntity(TokenEntity tokenEntity) {
    this.tokenEntity = tokenEntity;
  }
}
