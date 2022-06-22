package nl.brighton.zolder.resource.exception;

public class LocationNotFoundException extends Exception {

  public LocationNotFoundException() {
    super("Location not found");
  }
}
