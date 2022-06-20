package nl.brighton.zolder.service.location;

import java.util.List;
import nl.brighton.zolder.dto.Location;
import nl.brighton.zolder.dto.types.BuildingLocationType;
import nl.brighton.zolder.dto.types.InventoryLocationType;
import nl.brighton.zolder.resource.exception.LocationNotFoundException;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.UnknownLocationException;

public interface LocationService {

  List<Location> getLocations();

  List<Location> getLocation(BuildingLocationType buildingLocationType);

  Location getLocation(BuildingLocationType buildingLocationType,
      InventoryLocationType inventoryLocationType);

  Location getLocation(String Id) throws UnknownLocationException;

  Location addLocation(Location location) throws DuplicateLocationException;

  Location updateLocation(Location location) throws LocationNotFoundException;

  boolean removeLocation(Location location) throws UnknownLocationException;


}
