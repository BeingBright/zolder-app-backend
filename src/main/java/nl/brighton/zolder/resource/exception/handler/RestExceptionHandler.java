package nl.brighton.zolder.resource.exception.handler;

import nl.brighton.zolder.service.auth.exception.DuplicateTokenException;
import nl.brighton.zolder.service.auth.exception.InvalidTokenException;
import nl.brighton.zolder.service.book.exception.BookNotFoundException;
import nl.brighton.zolder.service.book.exception.DuplicateBookException;
import nl.brighton.zolder.service.location.exception.DuplicateLocationException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;
import nl.brighton.zolder.service.user.exception.DuplicateUserException;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class RestExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler({
          NoHandlerFoundException.class,
          BookNotFoundException.class,
          LocationNotFoundException.class,
          UserNotFoundException.class
  })
  public ResponseEntity<JSONException> notFoundHandler(Exception e, WebRequest webRequest) {
    LOGGER.warn("'{}' {}", e.getMessage(), webRequest.getDescription(false));
    return buildResponse(HttpStatus.NOT_FOUND, e);
  }

  @ExceptionHandler({
          Exception.class
  })
  public ResponseEntity<JSONException> internalServerHandler(Exception e, WebRequest webRequest) {
    LOGGER.error("'{}' {}", e.getMessage(), webRequest.getDescription(false));
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
  }

  @ExceptionHandler({
          DuplicateUserException.class,
          DuplicateTokenException.class,
          DuplicateLocationException.class,
          DuplicateBookException.class
  })
  public ResponseEntity<JSONException> notModifiedHandler(Exception e, WebRequest webRequest) {
    LOGGER.error("'{}' {}", e.getMessage(), webRequest.getDescription(false));
    return buildResponse(HttpStatus.NOT_MODIFIED, e);
  }

  @ExceptionHandler({
          AccessDeniedException.class,
          InvalidTokenException.class
  })
  public ResponseEntity<JSONException> forbiddenHandler(Exception e, WebRequest webRequest) {
    LOGGER.error("'{}' {}", e.getMessage(), webRequest.getDescription(false));
    return buildResponse(HttpStatus.UNAUTHORIZED, e);
  }

  private ResponseEntity<JSONException> buildResponse(HttpStatus status, Exception e) {
    return ResponseEntity.status(status)
            .body(new JSONException(e.getMessage(), status.toString(), status.value()));
  }

  public static class JSONException {

    private final String message;
    private final String status;
    private final int statusCode;

    public JSONException(String message, String status, int statsCode) {
      this.message = message;
      this.status = status;
      this.statusCode = statsCode;
    }

    public JSONException(String message) {
      this.message = message;
      this.status = HttpStatus.OK.toString();
      this.statusCode = 200;
    }

    public String getMessage() {
      return message;
    }

    public String getStatus() {
      return status;
    }

    public int getStatusCode() {
      return statusCode;
    }
  }
}
