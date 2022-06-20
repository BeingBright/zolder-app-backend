package nl.brighton.zolder.service.exception;

public class InvalidTokenException extends Exception {

  public InvalidTokenException() {
    super("Invalid authorization token");
  }
}
