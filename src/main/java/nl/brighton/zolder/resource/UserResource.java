package nl.brighton.zolder.resource;

import lombok.RequiredArgsConstructor;
import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.service.auth.AuthService;
import nl.brighton.zolder.service.exception.DuplicateUserException;
import nl.brighton.zolder.service.exception.InvalidTokenException;
import nl.brighton.zolder.service.exception.UserNotFoundException;
import nl.brighton.zolder.service.user.UserService;
import org.apache.el.stream.Stream;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "user")
public class UserResource {

  private final UserService userService;
  private final AuthService authService;


  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User[]> getUsers(@RequestHeader String authorization)
      throws InvalidTokenException {
    authService.isValid(authorization);
    var users = userService.getUsers().toArray(new User[0]);
//    for (int i = 0; i < users.length; i++) {
//      users[i].setPassword("");
//    }
    return ResponseEntity.ok(users);
  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> addUser(@RequestBody User user, @RequestHeader String authorization)
      throws InvalidTokenException, DuplicateUserException {
    authService.isValid(authorization);
    return ResponseEntity.ok(userService.addUser(user));
  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> updateUser(@RequestBody User user,
      @RequestHeader String authorization)
      throws UserNotFoundException, InvalidTokenException {
    authService.isValid(authorization);
    return ResponseEntity.ok(userService.updateUser(user));
  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity removeUser(@RequestBody User user,
      @RequestHeader String authorization) throws UserNotFoundException, InvalidTokenException {
    authService.isValid(authorization);
    userService.removeUser(user);
    return ResponseEntity.ok().build();
  }
}
