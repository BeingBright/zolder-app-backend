package nl.brighton.zolder.persistance;

import nl.brighton.zolder.dto.LocationAudit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationAuditRepository extends MongoRepository<LocationAudit, String> {
}
