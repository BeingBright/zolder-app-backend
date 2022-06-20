package nl.brighton.zolder.service.location.exception;

public class DuplicateLocationException extends
    Exception {

  public DuplicateLocationException() {
    super("Duplicate location");
  }
}
