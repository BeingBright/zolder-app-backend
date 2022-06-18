package nl.brighton.zolder.service;

import nl.brighton.zolder.dto.User;
import nl.brighton.zolder.dto.UserToken;
import nl.brighton.zolder.service.exception.InvalidTokenException;

public interface AuthService {
    boolean isValid(String token) throws InvalidTokenException;

    void removeToken(String token);

    UserToken generateToken(User user);

    void addToken(String token, UserToken userToken);
}
