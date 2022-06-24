package nl.brighton.zolder.service.location;

import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.Book;
import nl.brighton.zolder.model.Location;
import nl.brighton.zolder.persistance.LocationRepository;
import nl.brighton.zolder.service.book.BookService;
import nl.brighton.zolder.service.book.exception.BookNotFoundException;
import nl.brighton.zolder.service.book.exception.DuplicateBookException;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final BookService bookService;

    @Override
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    @Override
    public List<Location> getLocations(String buildingLocation) throws LocationNotFoundException {
        var locations = locationRepository.getLocationsByBuildingLocation(buildingLocation);
        if (locations != null) {
            return locations;
        }
        throw new LocationNotFoundException(buildingLocation);
    }

    @Override
    public Location getLocations(String buildingLocation, String inventoryLocation) throws LocationNotFoundException {
        var locations = locationRepository.getLocationsByBuildingLocationAndInventoryLocation(buildingLocation, inventoryLocation);
        if (locations != null) {
            return locations;
        }
        throw new LocationNotFoundException(buildingLocation);
    }

    @Override
    public Location getLocation(String locationId) throws LocationNotFoundException {
        var location = locationRepository.getLocationById(locationId);
        if (location != null) {
            return location;
        }
        throw new LocationNotFoundException(locationId);
    }

    @Override
    public Location addLocation(Location location) throws DuplicateLocationException {
        var locInDb = locationRepository.getLocationsByBuildingLocationAndInventoryLocation(location.getBuildingLocation(), location.getInventoryLocation());
        if (locInDb == null) {
            try {
                fillLocationWithBooks(location);
                saveBooksInLocation(location);
            } catch (DuplicateBookException e) {
                throw new RuntimeException(e);
            }
            return locationRepository.save(location);
        }
        throw new DuplicateLocationException(location);
    }

    @Override
    public Location updateLocation(Location location) throws LocationNotFoundException {
        var locInDb = locationRepository.getLocationById(location.getId());
        if (locInDb != null) {

            if ((location.getRowCount() - 1) * (location.getColumnCount() - 1)
                >= location.getBooks().size()) {
                try {
                    fillLocationWithBooks(location);
                    saveBooksInLocation(location);
                    System.out.println("Increase Book Count");
                } catch (DuplicateBookException e) {
                    throw new RuntimeException(e);
                }
            }
            return locationRepository.save(location);
        }
        throw new LocationNotFoundException(location);
    }

    @Override
    public boolean removeLocation(Location location) throws LocationNotFoundException {
        var locInDb = locationRepository.getLocationById(location.getId());
        if (locInDb != null) {
            for (Book book :
                locInDb.getBooks()) {
                try {
                    bookService.removeBook(book);
                } catch (BookNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            locationRepository.delete(locInDb);
            return true;
        }
        throw new LocationNotFoundException(location);
    }

    private void saveBooksInLocation(Location location) throws DuplicateBookException {
        var books = location.getBooks();
        for (int i = 0; i < books.size(); i++) {
            books.set(i, bookService.addBook(books.get(i)));
        }
        location.setBooks(books);
    }

    private void fillLocationWithBooks(Location location) {
        var findersBook = new Book();
        List<Book> books = location.getBooks() == null ? location.getBooks() : new ArrayList<>();
        for (int row = 0; row < location.getRowCount(); row++) {
            for (int column = 0; column < location.getColumnCount(); column++) {
                findersBook.setRow(row);
                findersBook.setColumn(column);
                if (!location.getBooks().contains(findersBook)) {
                    books.add(new Book(row, column, ""));
                }
            }
        }
        location.setBooks(books);
    }
}

