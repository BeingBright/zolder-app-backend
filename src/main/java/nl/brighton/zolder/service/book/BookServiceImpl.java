package nl.brighton.zolder.service.book;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.Book;
import nl.brighton.zolder.persistance.BookAuditRepository;
import nl.brighton.zolder.persistance.BookRepository;
import nl.brighton.zolder.service.book.exception.BookNotFoundException;
import nl.brighton.zolder.service.book.exception.DuplicateBookException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(String bookId) throws BookNotFoundException {
        var bookInDb = bookRepository.getByBookId(bookId);
        if (bookInDb != null) {
            return bookInDb;
        }
        throw new BookNotFoundException(bookId);
    }

    @Override
    public Book addBook(Book book) throws DuplicateBookException {
        var bookInDb = bookRepository.getById(book.getId());
        if (bookInDb == null) {
            return bookRepository.save(book);
        }
        throw new DuplicateBookException(book);
    }

    @Override
    public Book updateBook(Book book) throws BookNotFoundException {
        var bookInDb = bookRepository.getById(book.getId());
        if (bookInDb != null) {
            return bookRepository.save(book);
        }
        throw new BookNotFoundException(book);
    }

    @Override
    public boolean removeBook(Book book) throws BookNotFoundException {
        var bookInDb = bookRepository.getById(book.getId());
        if (bookInDb != null) {
            bookRepository.delete(book);
            return true;
        }
        throw new BookNotFoundException(book);
    }
}
