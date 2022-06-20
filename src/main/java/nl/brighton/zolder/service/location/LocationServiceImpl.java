package nl.brighton.zolder.service.location;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.dto.Location;
import nl.brighton.zolder.dto.types.BuildingLocationType;
import nl.brighton.zolder.dto.types.InventoryLocationType;
import nl.brighton.zolder.persistance.LocationRepository;
import nl.brighton.zolder.resource.exception.LocationNotFoundException;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.UnknownLocationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Setter(AccessLevel.NONE)
@Getter(AccessLevel.NONE)
public class LocationServiceImpl implements
    LocationService {

  private final LocationRepository locationRepository;

  @Override
  public List<Location> getLocations() {
    return locationRepository.findAll();
  }

  @Override
  public List<Location> getLocation(BuildingLocationType buildingLocationType) {
    return locationRepository.getLocationsByBuildingLocation(buildingLocationType);
  }

  @Override
  public Location getLocation(BuildingLocationType buildingLocationType,
      InventoryLocationType inventoryLocationType) {
    return locationRepository.getLocationsByBuildingLocationAndInventoryLocation(
        buildingLocationType, inventoryLocationType);
  }

  @Override
  public Location getLocation(String Id) throws UnknownLocationException {
    var loc = locationRepository.findById(Id);
    if (loc.isPresent()) {
      return loc.get();
    }
    throw new UnknownLocationException();
  }

  @Override
  public Location addLocation(Location location) throws DuplicateLocationException {
    var locInDb = locationRepository.getLocationsByBuildingLocationAndInventoryLocation(
        location.getBuildingLocation(), location.getInventoryLocation());
    if (!locInDb.equals(location)) {
      return locationRepository.save(location);
    }
    throw new DuplicateLocationException();
  }

  @Override
  public Location updateLocation(Location location) throws LocationNotFoundException {
    var locInDb = locationRepository.getLocationsByBuildingLocationAndInventoryLocation(
        location.getBuildingLocation(), location.getInventoryLocation());
    if (locInDb.getId().equals(location.getId())) {
      return locationRepository.save(location);
    }
    throw new LocationNotFoundException();
  }

  @Override
  public boolean removeLocation(Location location) throws UnknownLocationException {
    var locInDb = getLocation(location.getId());
    locationRepository.delete(locInDb);
    return true;
  }
}
