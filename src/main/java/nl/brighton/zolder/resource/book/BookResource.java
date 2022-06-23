package nl.brighton.zolder.resource.book;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.Book;
import nl.brighton.zolder.service.book.BookService;
import nl.brighton.zolder.service.book.exception.BookNotFoundException;
import nl.brighton.zolder.service.book.exception.DuplicateBookException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@Controller

@RequestMapping(path = "/book")
public class BookResource {

    private final BookService bookService;

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @ResponseBody
    @RequestMapping(path = "/{bookId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBook(@PathVariable String bookId) throws BookNotFoundException {
        return ResponseEntity.ok(bookService.getBook(bookId));
    }

    @PostAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> addBook(Book book) throws DuplicateBookException {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PostAuthorize("hasRole('ADMIN') or hasRole('WORKER')")
    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> updateBook(Book book) throws BookNotFoundException {
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @PostAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeBook(Book book) throws BookNotFoundException {
        bookService.removeBook(book);
        return ResponseEntity.ok().build();
    }

}
