package nl.brighton.zolder.persistance;

import nl.brighton.zolder.model.Location;
import nl.brighton.zolder.model.LocationAudit;
import nl.brighton.zolder.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocationAuditRepository extends MongoRepository<LocationAudit, String> {

    List<LocationAudit> getLocationAuditsByLocation(Location location);

    List<LocationAudit> getLocationAuditsByUser(User user);

}
