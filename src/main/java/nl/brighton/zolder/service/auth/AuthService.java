package nl.brighton.zolder.service.auth;

import nl.brighton.zolder.model.user.AuthToken;
import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.service.auth.exception.DuplicateTokenException;
import nl.brighton.zolder.service.auth.exception.InvalidTokenException;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;

public interface AuthService {
    boolean isValid(String token) throws InvalidTokenException;

    boolean removeToken(String token) throws InvalidTokenException;

    AuthToken generateToken(User user) throws UserNotFoundException;

    void addToken(AuthToken authToken) throws DuplicateTokenException;

    AuthToken getToken(String token) throws InvalidTokenException;
}
