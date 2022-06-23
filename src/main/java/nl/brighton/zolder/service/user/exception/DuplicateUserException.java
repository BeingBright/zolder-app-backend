package nl.brighton.zolder.service.user.exception;

public class DuplicateUserException extends Exception {
    public DuplicateUserException() {
        super("User Already Exists");
    }

    public DuplicateUserException(String username) {
        super("User: '" + username + "' Already Exists");
    }
}
