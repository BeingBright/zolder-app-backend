package nl.brighton.zolder.persistance;

import nl.brighton.zolder.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocationRepository extends MongoRepository<Location, String> {

    List<Location> getLocationsByBuildingLocation(String buildingLocation);

    List<Location> getLocationsByBuildingLocationAndInventoryLocation(String buildingLocation, String inventoryLocation);

    Location getLocationById(String Id);

}
