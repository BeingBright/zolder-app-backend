package nl.brighton.zolder.service.location;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.Location;
import nl.brighton.zolder.persistance.LocationRepository;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    @Override
    public List<Location> getLocations(String buildingLocation) throws LocationNotFoundException {
        var locations = locationRepository.getLocationsByBuildingLocation(buildingLocation);
        if (locations != null) {
            return locations;
        }
        throw new LocationNotFoundException(buildingLocation);
    }

    @Override
    public Location getLocations(String buildingLocation, String inventoryLocation) throws LocationNotFoundException {
        var locations = locationRepository.getLocationsByBuildingLocationAndInventoryLocation(buildingLocation, inventoryLocation);
        if (locations != null) {
            return locations;
        }
        throw new LocationNotFoundException(buildingLocation);
    }

    @Override
    public Location getLocation(String locationId) throws LocationNotFoundException {
        var location = locationRepository.getLocationById(locationId);
        if (location != null) {
            return location;
        }
        throw new LocationNotFoundException(locationId);
    }

    @Override
    public Location addLocation(Location location) throws DuplicateLocationException {
        var locInDb = locationRepository.getLocationsByBuildingLocationAndInventoryLocation(location.getBuildingLocation(), location.getInventoryLocation());
        if (locInDb == null) {
            return locationRepository.save(location);
        }
        throw new DuplicateLocationException(location);
    }

    @Override
    public Location updateLocation(Location location) throws LocationNotFoundException {
        var locInDb = locationRepository.getLocationById(location.getId());
        if (locInDb != null) {
            return locationRepository.save(location);
        }
        throw new LocationNotFoundException(location);
    }

    @Override
    public boolean removeLocation(Location location) throws LocationNotFoundException {
        var locInDb = locationRepository.getLocationById(location.getId());
        if (locInDb != null) {
            locationRepository.delete(locInDb);
            return true;
        }
        throw new LocationNotFoundException(location);
    }
}
