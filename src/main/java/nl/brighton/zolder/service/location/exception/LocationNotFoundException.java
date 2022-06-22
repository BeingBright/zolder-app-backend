package nl.brighton.zolder.service.location.exception;

import nl.brighton.zolder.model.Location;

public class LocationNotFoundException extends Exception {
    public LocationNotFoundException() {
        super("Location Not Found");
    }

    public LocationNotFoundException(String locationLocation) {
        super("Location on: '" + locationLocation + "' Not Found");
    }

    public LocationNotFoundException(Location location) {
        super("Location: '" + location + "' Not Found");
    }
}
