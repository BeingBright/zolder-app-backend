package nl.brighton.zolder.persistance.entity;

import nl.brighton.zolder.model.user.AuthToken;
import nl.brighton.zolder.model.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class HashmapTokenStore implements TokenEntity {

    private static final HashMap<String, AuthToken> tokens = new HashMap<>();

    private IRandomTokenGenerator randomTokenGenerator = () -> UUID.randomUUID().toString();


    @Override
    public boolean contains(String token) {
        return tokens.containsKey(token);
    }


    @Override
    public void addToken(String token, AuthToken authToken) {
        tokens.putIfAbsent(authToken.getToken(), authToken);
    }

    @Override
    public void removeToken(String token) {
        tokens.remove(token);
    }

    @Override
    public AuthToken generateToken(User user) {
        return new AuthToken(
                randomTokenGenerator.generate(),
                user.getUsername(),
                user.getRole()
        );
    }

    @Override
    public AuthToken getUserToken(String token) {
        return tokens.get(token);
    }

    @Override
    public List<AuthToken> getTokens() {
        return new ArrayList<>(tokens.values());
    }

    public void setRandomTokenGenerator(
            IRandomTokenGenerator randomTokenGenerator) {
        this.randomTokenGenerator = randomTokenGenerator;
    }
}
