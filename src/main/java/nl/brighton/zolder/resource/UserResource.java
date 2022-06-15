package nl.brighton.zolder.resource;

import java.util.Arrays;
import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.persistance.UserRepository;
import nl.brighton.zolder.resource.exception.DuplicateKey;
import nl.brighton.zolder.resource.exception.NoSuchUserException;
import nl.brighton.zolder.resource.exception.handler.RestExceptionHandler.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class UserResource {

  private UserRepository repository;


  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User[]> getUsers() {
    var users = repository.findAll().toArray(new User[0]);

    Arrays.stream(users).forEach(user -> user.setPassword(""));

    return ResponseEntity.ok(users);
  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> addUser(@RequestBody User user) throws DuplicateKey {

    try {
      return ResponseEntity.ok(repository.save(user));
    } catch (Exception ex) {
      throw new DuplicateKey(user.toString());
    }
  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<JSONException> removeUser(@RequestBody User user)
      throws  NoSuchUserException {
    if (repository.getByUsername(user.getUsername()) != null) {
      repository.delete(user);
      return ResponseEntity.ok(new JSONException(
          String.format("User with username: '%s' removed", user.getUsername())
      ));
    } else {
      throw new NoSuchUserException(
          String.format("User with username: '%s' does not exists", user.getUsername())
      );
    }


  }

  @Autowired
  public void setRepository(UserRepository repository) {
    this.repository = repository;
  }
}
