package nl.brighton.zolder.persistance;

import nl.brighton.zolder.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User getByUsername(String username);

    User getById(String Id);
}
