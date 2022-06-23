package nl.brighton.zolder.service.auth.exception;

public class DuplicateTokenException extends RuntimeException {
    public DuplicateTokenException() {
        super("Duplicate Token");
    }
}
