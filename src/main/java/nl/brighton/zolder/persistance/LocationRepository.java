package nl.brighton.zolder.persistance;

import nl.brighton.zolder.dto.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {


}
