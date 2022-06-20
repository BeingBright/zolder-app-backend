package nl.brighton.zolder.service.exception;

public class UserNotFoundException extends Exception {

  public UserNotFoundException() {
    super("User not found");
  }
}
