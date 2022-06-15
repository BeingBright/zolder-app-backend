package nl.brighton.zolder.resource.exception;

public class LocationNotFoundException extends Exception {

  public LocationNotFoundException(String id) {
    super(String.format("Location '%s' not found", id));
  }
}
