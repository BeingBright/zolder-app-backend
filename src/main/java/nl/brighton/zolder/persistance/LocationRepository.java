package nl.brighton.zolder.persistance;

import java.util.List;
import nl.brighton.zolder.dto.Location;
import nl.brighton.zolder.dto.types.BuildingLocationType;
import nl.brighton.zolder.dto.types.InventoryLocationType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {

  List<Location> getLocationsByBuildingLocation(BuildingLocationType buildingLocationType);

  Location getLocationsByBuildingLocationAndInventoryLocation(
      BuildingLocationType buildingLocationType,
      InventoryLocationType inventoryLocationType);


}
