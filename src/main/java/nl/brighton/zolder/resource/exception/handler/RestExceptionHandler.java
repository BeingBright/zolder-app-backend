package nl.brighton.zolder.resource.exception.handler;

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
      NoHandlerFoundException.class
  })
  public ResponseEntity<JSONException> notFoundHandler(Exception e, WebRequest webRequest) {
    LOGGER.warn("'{}' {}", e.getMessage(), webRequest.getDescription(false));
    return buildResponse(HttpStatus.NOT_FOUND, e);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<JSONException> internalServerHandler(Exception e, WebRequest webRequest) {
    LOGGER.error("'{}' {}", e.getMessage(), webRequest.getDescription(false));
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
  }


  @ExceptionHandler({AccessDeniedException.class})
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
