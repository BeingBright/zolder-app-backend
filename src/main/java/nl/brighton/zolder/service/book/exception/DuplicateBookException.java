package nl.brighton.zolder.service.book.exception;

import nl.brighton.zolder.model.Book;

public class DuplicateBookException extends Exception {

    public DuplicateBookException() {
        super("Duplicate Book Exception");
    }

    public DuplicateBookException(Book book) {
        super("Duplicate Book: '" + book + "'");
    }
}
