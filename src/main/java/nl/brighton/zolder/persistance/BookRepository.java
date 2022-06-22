package nl.brighton.zolder.persistance;

import nl.brighton.zolder.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {

    Book getByBookId(String bookId);

    Book getById(String Id);
}
