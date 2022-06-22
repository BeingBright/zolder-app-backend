package nl.brighton.zolder.service.location;

import nl.brighton.zolder.persistance.LocationRepository;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class LocationServiceImplTest {

    private LocationRepository mockedLocationRepository;
    private LocationServiceImpl sut;

    @BeforeEach
    void setUp() {
        sut = new LocationServiceImpl(mockedLocationRepository);
    }

    @Test
    void getLocations() {
//        var result = sut.getLocations();
        fail();
    }

    @Test
    void getLocationsWithBuildingLocation() {
//        var result = sut.getLocations();
        fail();
    }

    @Test
    void getLocationsWithBuildingLocationAndInventoryLocation() {
//        var result = sut.getLocations();
        fail();
    }

    @Test
    void getLocation() throws LocationNotFoundException {
//        var result = sut.getLocation("ID");
        fail();
    }

    @Test
    void addLocation() throws DuplicateLocationException {

//        var result = sut.addLocation(null);
        fail();
    }

    @Test
    void updateLocation() throws LocationNotFoundException {

//        var result = sut.updateLocation(null);
        fail();
    }


    @Test
    void removeLocation() throws LocationNotFoundException {

//        Assertions.assertTrue(sut.removeLocation(null));
        fail();
    }


}
