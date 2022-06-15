package nl.brighton.zolder.resource;

import com.mongodb.DuplicateKeyException;
import nl.brighton.zolder.dto.Location;
import nl.brighton.zolder.persistance.LocationRepository;
import nl.brighton.zolder.resource.exception.DuplicateKey;
import nl.brighton.zolder.resource.exception.LocationNotFoundException;
import nl.brighton.zolder.resource.exception.handler.RestExceptionHandler.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "location")
public class LocationResource {

  private LocationRepository repository;

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Location[]> getLocations() {
    return ResponseEntity.ok(repository.findAll().toArray(new Location[0]));
  }

  @ResponseBody
  @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Location> getLocation(@PathVariable String id)
      throws LocationNotFoundException {
    var location = repository.findById(id);
    if (!location.isPresent()) {
      throw new LocationNotFoundException(id);
    }
    return ResponseEntity.ok(location.get());
  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Location> addLocation(@RequestBody Location location)
      throws DuplicateKey {
    try {
      return ResponseEntity.ok(repository.save(location));
    } catch (Exception ex) {
      throw new DuplicateKey(location.toString());
    }

  }

  @ResponseBody
  @RequestMapping(path = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<JSONException> removeLocation(@RequestBody Location location) {
    repository.delete(location);
    return ResponseEntity.ok(new JSONException(
        "Removed location",
        HttpStatus.OK.toString(),
        200
    ));
  }

  @Autowired
  public void setRepository(LocationRepository repository) {
    this.repository = repository;
  }
}
