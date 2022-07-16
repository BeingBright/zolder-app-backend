package nl.brighton.zolder.resource.audit;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.Book;
import nl.brighton.zolder.model.Location;
import nl.brighton.zolder.model.LocationAudit;
import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.service.audit.AuditService;
import nl.brighton.zolder.service.book.exception.BookNotFoundException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Controller
@PostAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "/audit")
public class AuditResource {
    private final AuditService auditService;

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocationAudit>> getLocationAudit() {
        return ResponseEntity.ok(auditService.getLocationAudits());
    }

    @ResponseBody
    @RequestMapping(path = "/book", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocationAudit>> getLocationAudits(Book book) throws BookNotFoundException {
        return ResponseEntity.ok(auditService.getLocationAudits(book));
    }

    @ResponseBody
    @RequestMapping(path = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocationAudit>> getLocationAudits(User user) throws UserNotFoundException {
        return ResponseEntity.ok(auditService.getLocationAudits(user));
    }

    @ResponseBody
    @RequestMapping(path = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocationAudit>> getLocationAudits(Location location) throws LocationNotFoundException {
        return ResponseEntity.ok(auditService.getLocationAudits(location));
    }

}
