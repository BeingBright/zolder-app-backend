package nl.brighton.zolder.service.auth.exception;

public class DuplicateTokenException extends Exception {
    public DuplicateTokenException() {
        super("Duplicate Token");
    }
}
