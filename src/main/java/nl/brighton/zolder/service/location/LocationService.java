package nl.brighton.zolder.service.location;

import nl.brighton.zolder.model.Location;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;

import java.util.List;

public interface LocationService {
    List<Location> getLocations();

    List<Location> getLocations(String buildingLocation) throws LocationNotFoundException;

    Location getLocations(String buildingLocation, String inventoryLocation) throws LocationNotFoundException;

    Location getLocation(String locationId) throws LocationNotFoundException;

    Location addLocation(Location location) throws DuplicateLocationException;

    Location updateLocation(Location location) throws LocationNotFoundException;

    boolean removeLocation(Location location) throws LocationNotFoundException;
}
