package nl.brighton.zolder.resource;

import lombok.RequiredArgsConstructor;
import nl.brighton.zolder.dto.Book;
import nl.brighton.zolder.dto.Location;
import nl.brighton.zolder.dto.types.BuildingLocationType;
import nl.brighton.zolder.dto.types.InventoryLocationType;
import nl.brighton.zolder.resource.exception.LocationNotFoundException;
import nl.brighton.zolder.service.auth.AuthService;
import nl.brighton.zolder.service.exception.InvalidTokenException;
import nl.brighton.zolder.service.location.LocationService;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.UnknownLocationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "location")
public class LocationResource {

    private final LocationService locationService;
    private final AuthService authService;

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Location>> getAllLocations(@RequestHeader String authorization) throws InvalidTokenException {
        authService.isValid(authorization);
        return ResponseEntity.ok(locationService.getLocations());
    }

    @ResponseBody
    @RequestMapping(path = "/building/{buildingType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Location>> getLocationByBuildingType(@RequestHeader String authorization, @PathVariable BuildingLocationType buildingType) throws InvalidTokenException {
        authService.isValid(authorization);
        return ResponseEntity.ok(locationService.getLocation(buildingType));
    }

    @ResponseBody
    @RequestMapping(path = "/{Id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Location> getLocationById(@RequestHeader String authorization, @PathVariable String Id) throws UnknownLocationException, InvalidTokenException {
        authService.isValid(authorization);
        return ResponseEntity.ok(locationService.getLocation(Id));

    }

    @ResponseBody
    @RequestMapping(path = "/{buildingType}/{inventoryType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Location> getLocationByBuildingTypeAndInventoryType(@RequestHeader String authorization, @PathVariable BuildingLocationType buildingType, @PathVariable InventoryLocationType inventoryType) throws InvalidTokenException {
        authService.isValid(authorization);
        return ResponseEntity.ok(locationService.getLocation(buildingType, inventoryType));
    }

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Location> addLocation(@RequestHeader String authorization, @RequestBody Location location) throws DuplicateLocationException, InvalidTokenException {
        authService.isValid(authorization);
        return ResponseEntity.ok(locationService.addLocation(location));
    }


    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Location> updateLocation(@RequestHeader String authorization, @RequestBody Location location) throws LocationNotFoundException, InvalidTokenException {
        authService.isValid(authorization);
        return ResponseEntity.ok(locationService.updateLocation(location));
    }


    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Boolean> removeLocation(@RequestHeader String authorization, @RequestBody Location location) throws UnknownLocationException, InvalidTokenException {
        authService.isValid(authorization);
        return ResponseEntity.ok(locationService.removeLocation(location));
    }

}
