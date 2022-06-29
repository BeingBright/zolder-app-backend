package nl.brighton.zolder.resource.book;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.brighton.zolder.model.Book;
import nl.brighton.zolder.resource.WebSocketMessagingController;
import nl.brighton.zolder.service.audit.AuditService;
import nl.brighton.zolder.service.auth.AuthService;
import nl.brighton.zolder.service.auth.exception.InvalidTokenException;
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

    private final AuthService authService;
    private final AuditService auditService;
    private final WebSocketMessagingController messagingController;
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
    public ResponseEntity<Book> addBook(@RequestBody Book book) throws DuplicateBookException {
        var savedBook = bookService.addBook(book);
        messagingController.sendUpdate("/book");
        return ResponseEntity.ok(savedBook);
    }

    @PostAuthorize("hasRole('ADMIN') or hasRole('WORKER')")
    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @RequestHeader String authorization) throws BookNotFoundException, InvalidTokenException {
        var savedBook = bookService.updateBook(book);
        var authToken = authService.getToken(authorization);
        auditService.addBookAudit(savedBook, authToken.getUsername());
        messagingController.sendUpdate("/book");
        return ResponseEntity.ok(savedBook);
    }

    @PostAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeBook(@RequestBody Book book) throws BookNotFoundException {
        bookService.removeBook(book);
        messagingController.sendUpdate("/book");
        return ResponseEntity.ok().build();
    }

}
