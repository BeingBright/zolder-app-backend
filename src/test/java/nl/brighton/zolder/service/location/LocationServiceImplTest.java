package nl.brighton.zolder.service.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import nl.brighton.zolder.dto.BookInLocation;
import nl.brighton.zolder.dto.Location;
import nl.brighton.zolder.dto.types.BuildingLocationType;
import nl.brighton.zolder.dto.types.InventoryLocationType;
import nl.brighton.zolder.persistance.LocationRepository;
import nl.brighton.zolder.resource.exception.LocationNotFoundException;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.UnknownLocationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationServiceImplTest {

  private LocationServiceImpl sut;
  private LocationRepository mockedLocationRepository;

  @BeforeEach
  void setUp() {
    mockedLocationRepository = mock(LocationRepository.class);

    when(mockedLocationRepository.findById("Present")).thenReturn(Optional.of(new Location()));
    when(mockedLocationRepository.findById("NOT Present")).thenReturn(Optional.empty());

    when(mockedLocationRepository.getLocationsByBuildingLocationAndInventoryLocation(any(),
        any())).thenReturn(
        new Location("", 1, 1, BuildingLocationType.CELC, InventoryLocationType.W,
            new BookInLocation[0]));

    sut = new LocationServiceImpl(mockedLocationRepository);
  }

  @Test
  void getAllLocations() {
    sut.getLocations();
    verify(mockedLocationRepository).findAll();
  }

  @Test
  void getLocationById() throws UnknownLocationException {
    var result = sut.getLocation("Present");
    verify(mockedLocationRepository).findById(anyString());
    assertEquals(new Location(), result);

    assertThrows(UnknownLocationException.class, () -> sut.getLocation("NOT Present"));
  }

  @Test
  void getLocationByBuildingType() {
    var result = sut.getLocation(BuildingLocationType.CELC);
    verify(mockedLocationRepository).getLocationsByBuildingLocation(any());
  }

  @Test
  void getLocationByBuildingTypeAndInventoryType() {
    var result = sut.getLocation(BuildingLocationType.CELC, InventoryLocationType.W);
    verify(mockedLocationRepository).getLocationsByBuildingLocationAndInventoryLocation(any(),
        any());
  }

  @Test
  void addLocation() throws DuplicateLocationException {
    var dupLoc = new Location("", 1, 1, BuildingLocationType.CELC, InventoryLocationType.W,
        new BookInLocation[0]);
    sut.addLocation(new Location());
    verify(mockedLocationRepository).save(any());
    assertThrows(DuplicateLocationException.class, () -> sut.addLocation(dupLoc));
  }

  @Test
  void updateLocation() throws LocationNotFoundException {
    var dupLoc = new Location("", 1, 1, BuildingLocationType.CELC, InventoryLocationType.W,
        new BookInLocation[0]);
    sut.updateLocation(dupLoc);
    verify(mockedLocationRepository).save(any());
    assertThrows(LocationNotFoundException.class, () -> sut.updateLocation(new Location()));
  }

  @Test
  void removeLocation() throws UnknownLocationException {
    var loc = new Location("Present", 1, 1, BuildingLocationType.CELC, InventoryLocationType.W,
        new BookInLocation[0]);
    sut.removeLocation(loc);
    assertThrows(UnknownLocationException.class, () -> sut.removeLocation(new Location()));
  }
}