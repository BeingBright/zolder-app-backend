package nl.brighton.zolder.persistance.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.user.AuthToken;
import nl.brighton.zolder.model.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
@Setter
@Getter
public class DatabaseTokenStore implements TokenEntity {

    private final AuthTokenRepository tokenRepository;
    private IRandomTokenGenerator randomTokenGenerator = () -> UUID.randomUUID().toString();

    @Override
    public boolean contains(String token) {
        return tokenRepository.getByToken(token) != null;
    }

    @Override
    public void addToken(String token, AuthToken authToken) {
        tokenRepository.save(authToken);
    }

    @Override
    public void removeToken(String token) {
        tokenRepository.deleteAuthTokenByToken(token);
    }

    @Override
    public AuthToken generateToken(User user) {
        return new AuthToken(
                randomTokenGenerator.generate(),
                user.getUsername(),
                user.getRole(),
                user.isRememberMe()
        );
    }

    @Override
    public AuthToken getUserToken(String token) {
        return tokenRepository.getByToken(token);
    }

    @Override
    public List<AuthToken> getTokens() {
        return tokenRepository.findAll();
    }
}
