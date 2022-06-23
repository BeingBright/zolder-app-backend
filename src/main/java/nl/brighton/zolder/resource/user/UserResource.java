package nl.brighton.zolder.resource.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.service.user.UserService;
import nl.brighton.zolder.service.user.exception.DuplicateUserException;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Controller
@PostAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "/user")
public class UserResource {

    private final UserService userService;

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @ResponseBody
    @RequestMapping(path = "/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable String username) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) throws DuplicateUserException {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody User user) throws UserNotFoundException {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeUser(@RequestBody User user) throws UserNotFoundException {
        userService.removeUser(user);
        return ResponseEntity.ok().build();
    }


}
