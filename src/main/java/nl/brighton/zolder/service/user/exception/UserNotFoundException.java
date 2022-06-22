package nl.brighton.zolder.service.user.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String username) {
        super("User: '" + username + "' Not Found");
    }
}
