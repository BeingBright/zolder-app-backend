package nl.brighton.zolder.service.exception;

public class DuplicateUserException extends Exception {

  public DuplicateUserException() {
    super("User already Exists");
  }
}
