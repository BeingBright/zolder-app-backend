package nl.brighton.zolder.service.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.user.AuthToken;
import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.persistance.entity.TokenEntity;
import nl.brighton.zolder.service.auth.exception.DuplicateTokenException;
import nl.brighton.zolder.service.auth.exception.InvalidTokenException;
import nl.brighton.zolder.service.user.UserService;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

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
            return true;
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
        if (userInDb != null && !userInDb.isActive() && userInDb.equals(user)) {
            var token = generateToken(user);
            addToken(token);
            return token;
        }
        throw new UserNotFoundException(user.getUsername());
    }

    @Override
    public boolean addToken(AuthToken authToken) throws DuplicateTokenException {
        if (tokenEntity.contains(authToken.getToken())) {
            tokenEntity.addToken(authToken.getToken(), authToken);
            return true;
        }
        throw new DuplicateTokenException();
    }
}
