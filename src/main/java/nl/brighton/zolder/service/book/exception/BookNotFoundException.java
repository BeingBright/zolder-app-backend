package nl.brighton.zolder.service.book.exception;

import nl.brighton.zolder.model.Book;

public class BookNotFoundException extends Exception {
    public BookNotFoundException() {
        super("Book Not Found");
    }

    public BookNotFoundException(String bookID) {
        super("Book: '" + bookID + "' Not Found");
    }

    public BookNotFoundException(Book book) {
        super("Book: '" + book + "' Not Found");
    }
}
