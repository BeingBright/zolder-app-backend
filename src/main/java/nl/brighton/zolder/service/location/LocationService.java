package nl.brighton.zolder.service.location;

import java.util.List;
import nl.brighton.zolder.dto.Location;
import nl.brighton.zolder.dto.types.BuildingLocationType;
import nl.brighton.zolder.dto.types.InventoryLocationType;

public interface LocationService {

  List<Location> getLocations();

  List<Location> getLocation(BuildingLocationType buildingLocationType);

  List<Location> getLocation(BuildingLocationType buildingLocationType,
      InventoryLocationType inventoryLocationType);

}
