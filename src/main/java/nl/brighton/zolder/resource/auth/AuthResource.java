package nl.brighton.zolder.resource.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.user.AuthToken;
import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.service.auth.AuthService;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@RestController
@RequestMapping(path = "/auth")
public class AuthResource {

    private final AuthService authService;

    @ResponseBody
    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthToken> generateToken(@RequestBody User user) throws UserNotFoundException {
        AuthToken authToken = authService.generateToken(user);
        return ResponseEntity.ok(authToken);
    }

    @PostAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @RequestMapping(path = "/ping", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> ping() throws UserNotFoundException {
        return ResponseEntity.ok("PONG");
    }

}
