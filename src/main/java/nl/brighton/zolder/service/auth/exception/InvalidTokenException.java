package nl.brighton.zolder.service.auth.exception;

public class InvalidTokenException extends Exception {
    public InvalidTokenException() {
        super("Invalid Token");
    }

}
