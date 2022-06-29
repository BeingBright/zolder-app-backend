package nl.brighton.zolder.service.audit;

import nl.brighton.zolder.model.Book;
import nl.brighton.zolder.model.Location;
import nl.brighton.zolder.model.LocationAudit;
import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.service.book.exception.BookNotFoundException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;

import java.util.List;

public interface AuditService {
    List<LocationAudit> getLocationAudits();

    List<LocationAudit> getLocationAudits(Location location) throws LocationNotFoundException;

    List<LocationAudit> getLocationAudits(User user) throws UserNotFoundException;

    List<LocationAudit> getLocationAudits(Book book) throws BookNotFoundException;

    void addBookAudit(Book book, String username);
}
