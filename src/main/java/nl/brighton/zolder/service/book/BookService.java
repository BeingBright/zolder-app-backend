package nl.brighton.zolder.service.book;

import nl.brighton.zolder.model.Book;
import nl.brighton.zolder.model.Location;
import nl.brighton.zolder.service.book.exception.BookNotFoundException;
import nl.brighton.zolder.service.book.exception.DuplicateBookException;
import nl.brighton.zolder.service.location.exception.LocationNotFoundException;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    List<Book> getBooks(Location location) throws LocationNotFoundException;

    Book getBook(String bookId) throws BookNotFoundException;

    boolean addBook(Book book) throws DuplicateBookException;

    boolean updateBook(Book book) throws BookNotFoundException;

    boolean removeBook(Book book) throws BookNotFoundException;
}
