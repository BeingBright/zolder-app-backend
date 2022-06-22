package nl.brighton.zolder.service.location.exception;

import nl.brighton.zolder.model.Location;

public class DuplicateLocationException extends Exception {
    public DuplicateLocationException() {
        super("Duplicate Location");
    }

    public DuplicateLocationException(Location location) {
        super("Duplicate location:" + location);
    }
}
