package nl.brighton.zolder.service.location;

import nl.brighton.zolder.model.Location;
import nl.brighton.zolder.persistance.LocationRepository;
import nl.brighton.zolder.service.book.BookService;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LocationServiceImplTest {

    private LocationRepository mockedLocationRepository;
    private BookService mockedBookService;
    private LocationServiceImpl sut;

    @BeforeEach
    void setUp() {
        mockedLocationRepository = mock(LocationRepository.class);
        mockedBookService = mock(BookService.class);

        when(mockedLocationRepository.getLocationById("ID1")).thenReturn(getPreBuildLocation());
        when(mockedLocationRepository.getLocationById("NOT HERE")).thenReturn(null);

        when(mockedLocationRepository.getLocationsByBuildingLocation("loc1")).thenReturn(getLocationList());
        when(mockedLocationRepository.getLocationsByBuildingLocation("NOT HERE")).thenReturn(null);

        when(mockedLocationRepository.getLocationsByBuildingLocationAndInventoryLocation("loc1", "inv1")).thenReturn(getPreBuildLocation());
        when(mockedLocationRepository.getLocationsByBuildingLocationAndInventoryLocation("NOT HERE", "NOT THERE")).thenReturn(null);

        when(mockedLocationRepository.save(new Location())).thenReturn(new Location());
        when(mockedLocationRepository.save(getPreBuildLocation())).thenReturn(getPreBuildLocation());

        when(mockedLocationRepository.findAll()).thenReturn(getLocationList());

        sut = new LocationServiceImpl(mockedLocationRepository, mockedBookService);
    }

    @Test
    void getLocations() {
        var result = sut.getLocations();
        assertEquals(getLocationList(), result);
    }

    @Test
    void getLocationsWithBuildingLocation() throws LocationNotFoundException {
        var result = sut.getLocations("loc1");
        assertEquals(getLocationList(), result);

        assertThrows(LocationNotFoundException.class, () -> sut.getLocations("NOT HERE"));
    }

    @Test
    void getLocationsWithBuildingLocationAndInventoryLocation() throws LocationNotFoundException {
        var result = sut.getLocations("loc1", "inv1");
        assertEquals(getPreBuildLocation(), result);

        assertThrows(LocationNotFoundException.class, () -> sut.getLocations("NOT HERE", "NOT THERE"));

    }

    @Test
    void getLocation() throws LocationNotFoundException {
        var result = sut.getLocation("ID1");
        assertEquals(getPreBuildLocation(), result);

        assertThrows(LocationNotFoundException.class, () -> sut.getLocation("NOT HERE"));
    }

    @Test
    void addLocation() throws DuplicateLocationException {

        var result = sut.addLocation(new Location());
        assertEquals(new Location(), result);

        assertThrows(DuplicateLocationException.class, () -> sut.addLocation(getPreBuildLocation()));
    }

    @Test
    void updateLocation() throws LocationNotFoundException {

        var result = sut.updateLocation(getPreBuildLocation());
        assertEquals(getPreBuildLocation(), result);

        assertThrows(LocationNotFoundException.class, () -> sut.updateLocation(new Location()));
    }


    @Test
    void removeLocation() throws LocationNotFoundException {

        var result = sut.removeLocation(getPreBuildLocation());
        assertEquals(true, result);

        assertThrows(LocationNotFoundException.class, () -> sut.removeLocation(new Location()));
    }

    private List<Location> getLocationList() {
        return new ArrayList<>(Arrays.asList(
                new Location("ID1", 2, 3, "loc1", "inv1", new ArrayList<>()),
                new Location("ID2", 2, 3, "loc2", "inv2", new ArrayList<>()),
                new Location("ID3", 2, 3, "loc3", "inv3", new ArrayList<>()),
                new Location("ID4", 2, 3, "loc4", "inv4", new ArrayList<>())
        ));
    }

    private Location getPreBuildLocation() {
        return new Location("ID1", 2, 3, "loc1", "inv1", new ArrayList<>());
    }

}
