package nl.brighton.zolder.service.location;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import nl.brighton.zolder.persistance.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationServiceImplTest {

  private LocationServiceImpl sut;
  private LocationRepository mockedLocationRepository;

  @BeforeEach
  void setUp() {
    mockedLocationRepository = mock(LocationRepository.class);
    sut = new LocationServiceImpl(mockedLocationRepository);
  }

  @Test
  void getLocations() {
    fail();
  }

  @Test
  void getLocation() {
    fail();
  }

  @Test
  void testGetLocation() {
    fail();
  }

  @Test
  void testGetLocation1() {
    fail();
  }

  @Test
  void addLocation() {
    fail();
  }

  @Test
  void updateLocation() {
    fail();
  }

  @Test
  void removeLocation() {
    fail();
  }
}