package nl.brighton.zolder.resource;

import nl.brighton.zolder.dto.Location;
import nl.brighton.zolder.dto.LocationAudit;
import nl.brighton.zolder.persistance.LocationAuditRepository;
import nl.brighton.zolder.persistance.LocationRepository;
import nl.brighton.zolder.persistance.entity.TokenEntity;
import nl.brighton.zolder.resource.exception.DuplicateKey;
import nl.brighton.zolder.resource.exception.LocationNotFoundException;
import nl.brighton.zolder.resource.exception.handler.RestExceptionHandler.JSONException;
import nl.brighton.zolder.service.AuthService;
import nl.brighton.zolder.service.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "location")
public class LocationResource {

  private LocationRepository locationRepository;
  private LocationAuditRepository locationAuditRepository;
  private AuthService authService;

  private TokenEntity tokenEntity;

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Location[]> getLocations(@RequestHeader String token)
      throws InvalidTokenException {
    authService.isValid(token);
    System.out.println(token);
    return ResponseEntity.ok(locationRepository.findAll().toArray(new Location[0]));
  }

  @ResponseBody
  @RequestMapping(path = "/{inventory}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Location[]> getLocationsByInventory(@RequestHeader String token,
      @PathVariable String inventory) throws InvalidTokenException {
    authService.isValid(token);
    System.out.println(token);
    return ResponseEntity.ok(locationRepository.findAll().toArray(new Location[0]));
  }

  @ResponseBody
  @RequestMapping(path = "/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Location> getLocation(@PathVariable String id, @RequestHeader String token)
      throws LocationNotFoundException, InvalidTokenException {
    authService.isValid(token);
    var location = locationRepository.findById(id);
    if (!location.isPresent()) {
      throw new LocationNotFoundException(id);
    }
    return ResponseEntity.ok(location.get());
  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Location> addLocation(@RequestBody Location location,
      @RequestHeader String token)
      throws DuplicateKey, InvalidTokenException {
    authService.isValid(token);
    try {
      var savedLocation = locationRepository.save(location);
      locationAuditRepository.save(new LocationAudit(location, savedLocation.getBookNumber(),
          tokenEntity.getUserToken(token).getUser()));
      return ResponseEntity.ok(location);
    } catch (Exception ex) {
      throw new DuplicateKey(location.toString());
    }

  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<JSONException> removeLocation(@RequestBody Location location,
      @RequestHeader String token) throws InvalidTokenException {
    authService.isValid(token);
    locationRepository.delete(location);
    return ResponseEntity.ok(new JSONException(
        "Removed location",
        HttpStatus.OK.toString(),
        200
    ));
  }

  @ResponseBody
  @RequestMapping(path = "/audit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LocationAudit[]> getAuditLogs(@RequestHeader String token)
      throws InvalidTokenException {
    authService.isValid(token);
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
  public void setAuthService(AuthService authService) {
    this.authService = authService;
  }

  @Autowired
  public void setTokenEntity(TokenEntity tokenEntity) {
    this.tokenEntity = tokenEntity;
  }
}
