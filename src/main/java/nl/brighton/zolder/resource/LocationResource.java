package nl.brighton.zolder.resource;

import nl.brighton.zolder.dto.Location;
import nl.brighton.zolder.dto.LocationAudit;
import nl.brighton.zolder.persistance.LocationAuditRepository;
import nl.brighton.zolder.persistance.LocationRepository;
import nl.brighton.zolder.persistance.entity.TokenEntity;
import nl.brighton.zolder.resource.exception.DuplicateKey;
import nl.brighton.zolder.resource.exception.LocationNotFoundException;
import nl.brighton.zolder.resource.exception.handler.RestExceptionHandler.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "location")
public class LocationResource{

  private LocationRepository locationRepository;
  private LocationAuditRepository locationAuditRepository;
  private TokenEntity tokenEntity;

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Location[]> getLocations(@RequestHeader String token) {
    tokenEntity.contains(token);
    return ResponseEntity.ok(locationRepository.findAll().toArray(new Location[0]));
  }

  @ResponseBody
  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Location> getLocation(@PathVariable String id, @RequestHeader String token)
          throws LocationNotFoundException {
    tokenEntity.contains(token);
    var location = locationRepository.findById(id);
    if (!location.isPresent()) {
      throw new LocationNotFoundException(id);
    }
    return ResponseEntity.ok(location.get());
  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Location> addLocation(@RequestBody Location location, @RequestHeader String token)
          throws DuplicateKey {
    tokenEntity.contains(token);
    try {
      var savedLocation = locationRepository.save(location);
      locationAuditRepository.save(new LocationAudit(location, savedLocation.getBookNumber(), tokenEntity.getUserToken(token).getUser()));
      return ResponseEntity.ok(location);
    } catch (Exception ex) {
      throw new DuplicateKey(location.toString());
    }

  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<JSONException> removeLocation(@RequestBody Location location, @RequestHeader String token) {
    tokenEntity.contains(token);
    locationRepository.delete(location);
    return ResponseEntity.ok(new JSONException(
            "Removed location",
            HttpStatus.OK.toString(),
            200
    ));
  }

  @ResponseBody
  @RequestMapping(path = "/audit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LocationAudit[]> getAuditLogs(@RequestHeader String token) {
    tokenEntity.contains(token);
    return ResponseEntity.ok(locationAuditRepository.findAll().toArray(new LocationAudit[0]));
  }

  @Autowired
  public void setLocationRepository(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  @Autowired
  public void setLocationAuditRepository(LocationAuditRepository locationAuditRepository) {
    this.locationAuditRepository = locationAuditRepository;
  }

  @Autowired
  public void setTokenEntity(TokenEntity tokenEntity) {
    this.tokenEntity = tokenEntity;
  }
}
