package nl.brighton.zolder.resource.location;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.Location;
import nl.brighton.zolder.resource.WebSocketMessagingController;
import nl.brighton.zolder.service.location.LocationService;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Controller
@RequestMapping(path = "/location")
public class LocationResource {

    private final WebSocketMessagingController messagingController;
    private final LocationService locationService;

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Location>> getLocations() {
        return ResponseEntity.ok(locationService.getLocations());
    }

    @ResponseBody
    @RequestMapping(path = "/location/{buildingLocation}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Location>> getLocations(@PathVariable String buildingLocation) throws LocationNotFoundException {
        return ResponseEntity.ok(locationService.getLocations(buildingLocation));
    }

    @ResponseBody
    @RequestMapping(path = "/location/{buildingLocation}/inventoryLocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> getLocations(@PathVariable String buildingLocation, @PathVariable String inventoryLocation) throws LocationNotFoundException {
        return ResponseEntity.ok(locationService.getLocations(buildingLocation, inventoryLocation));
    }

    @ResponseBody
    @RequestMapping(path = "/{locationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> getLocation(@PathVariable String locationId) throws LocationNotFoundException {
        return ResponseEntity.ok(locationService.getLocation(locationId));
    }

    @PostAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> addLocation(@RequestBody Location location) throws DuplicateLocationException {
        messagingController.sendUpdate("/location");
        return ResponseEntity.ok(locationService.addLocation(location));
    }

    @PostAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> updateLocation(@RequestBody Location location) throws LocationNotFoundException {
        messagingController.sendUpdate("/location");
        return ResponseEntity.ok(locationService.updateLocation(location));
    }

    @PostAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeLocation(@RequestBody Location location) throws LocationNotFoundException {
        messagingController.sendUpdate("/location");
        locationService.removeLocation(location);
        return ResponseEntity.ok().build();
    }
}
