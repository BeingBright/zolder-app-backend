package nl.brighton.zolder.persistance.entity;

import nl.brighton.zolder.model.user.AuthToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthTokenRepository extends MongoRepository<AuthToken, String> {
    AuthToken getByToken(String token);

    void deleteAuthTokenByToken(String token);

}
