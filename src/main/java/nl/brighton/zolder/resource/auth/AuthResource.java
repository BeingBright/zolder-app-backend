package nl.brighton.zolder.resource.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.user.AuthToken;
import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.service.auth.AuthService;
import nl.brighton.zolder.service.auth.exception.InvalidTokenException;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Controller
@RequestMapping(path = "/auth")
public class AuthResource {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final AuthService authService;

    @ResponseBody
    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthToken> generateToken(@RequestBody User user) throws UserNotFoundException {
        AuthToken authToken = authService.generateToken(user);
        simpMessagingTemplate.convertAndSend("/topic/greetings","Send to all users");
        return ResponseEntity.ok(authToken);
    }

    @ResponseBody
    @RequestMapping(path = "/logout", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> logout(@RequestHeader String authorization) throws InvalidTokenException {
        authService.removeToken(authorization);
        return ResponseEntity.ok().build();
    }

    // /app/hello
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String PingSTOMP() throws Exception {

        return "PONG SEND BY STOMPING";
    }

}
