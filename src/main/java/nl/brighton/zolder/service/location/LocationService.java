package nl.brighton.zolder.service.location;

import nl.brighton.zolder.model.Location;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;

import java.util.List;

public interface LocationService {
    List<Location> getLocations();

    List<Location> getLocations(String buildingLocation) throws LocationNotFoundException;

    List<Location> getLocations(String buildingLocation, String inventoryLocation) throws LocationNotFoundException;

    List<Location> getLocation(String locationId) throws LocationNotFoundException;

    boolean addLocation(Location location) throws DuplicateLocationException;

    boolean removeLocation(Location location) throws LocationNotFoundException;

    boolean updateLocation(Location location) throws LocationNotFoundException;
}
