package nl.brighton.zolder.persistance;

import nl.brighton.zolder.model.BookAudit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookAuditRepository extends MongoRepository<BookAudit, String> {
    List<BookAudit> getByBookId(String bookId);
}
