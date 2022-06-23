package nl.brighton.zolder.service.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.user.AuthToken;
import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.persistance.entity.TokenEntity;
import nl.brighton.zolder.service.auth.exception.InvalidTokenException;
import nl.brighton.zolder.service.user.UserService;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final TokenEntity tokenEntity;

    @Override
    public boolean isValid(String token) throws InvalidTokenException {
        if (tokenEntity.contains(token)) {
            Date expireDate = tokenEntity.getUserToken(token).getExpireDate();
            Date now = new Date();
            if (expireDate.after(now)) {
                return true;
            } else {
                tokenEntity.removeToken(token);
            }

        }
        throw new InvalidTokenException();
    }

    @Override
    public boolean removeToken(String token) throws InvalidTokenException {
        if (tokenEntity.contains(token)) {
            tokenEntity.removeToken(token);
            return true;
        }
        throw new InvalidTokenException();
    }

    @Override
    public AuthToken generateToken(User user) throws UserNotFoundException {
        var userInDb = userService.getUser(user.getUsername());
        if (userInDb != null && userInDb.isActive() && userInDb.equals(user)) {
            var token = tokenEntity.generateToken(userInDb);
            addToken(token);
            return token;
        }
        throw new UserNotFoundException(user.getUsername());
    }

    @Override
    public void addToken(AuthToken authToken) {
        tokenEntity.addToken(authToken.getToken(), authToken);
    }

    @Override
    public AuthToken getToken(String token) throws InvalidTokenException {
        var authToken = tokenEntity.getUserToken(token);
        if (authToken != null) {
            return authToken;
        }
        throw new InvalidTokenException();
    }
}
