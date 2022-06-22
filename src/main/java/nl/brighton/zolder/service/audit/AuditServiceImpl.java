package nl.brighton.zolder.service.audit;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.Book;
import nl.brighton.zolder.model.Location;
import nl.brighton.zolder.model.LocationAudit;
import nl.brighton.zolder.model.user.User;
import nl.brighton.zolder.persistance.LocationAuditRepository;
import nl.brighton.zolder.service.book.exception.BookNotFoundException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;
import nl.brighton.zolder.service.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Service
public class AuditServiceImpl implements AuditService {

    private final LocationAuditRepository auditRepository;

    @Override
    public List<LocationAudit> getLocationAudits() {
        return auditRepository.findAll();
    }

    @Override
    public List<LocationAudit> getLocationAudits(Location location) throws LocationNotFoundException {
        return auditRepository.getLocationAuditsByLocation(location);
    }

    @Override
    public List<LocationAudit> getLocationAudits(User user) throws UserNotFoundException {
        return auditRepository.getLocationAuditsByUser(user);
    }

    @Override
    public List<LocationAudit> getLocationAudits(Book book) throws BookNotFoundException {
        return auditRepository.getLocationAuditsByBookInLocation(book);
    }
}
