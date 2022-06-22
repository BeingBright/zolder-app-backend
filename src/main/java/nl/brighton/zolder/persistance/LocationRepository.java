package nl.brighton.zolder.persistance;

import nl.brighton.zolder.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location, String> {
}
