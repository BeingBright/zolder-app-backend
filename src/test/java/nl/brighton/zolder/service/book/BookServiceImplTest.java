package nl.brighton.zolder.service.book;

import nl.brighton.zolder.model.Book;
import nl.brighton.zolder.persistance.BookRepository;
import nl.brighton.zolder.service.book.exception.BookNotFoundException;
import nl.brighton.zolder.service.book.exception.DuplicateBookException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    private BookRepository mockedBookRepository;
    private BookService sut;

    @BeforeEach
    void setUp() {
        mockedBookRepository = mock(BookRepository.class);
        when(mockedBookRepository.findAll()).thenReturn(getBookList());

        when(mockedBookRepository.getByBookId("Pre Build")).thenReturn(getPrebuildBook());
        when(mockedBookRepository.getByBookId("Not Build")).thenReturn(null);

        when(mockedBookRepository.getById("ID1")).thenReturn(getPrebuildBook());
        when(mockedBookRepository.getById(null)).thenReturn(null);

        when(mockedBookRepository.save(new Book())).thenReturn(new Book());
        when(mockedBookRepository.save(getPrebuildBook())).thenReturn(getPrebuildBook());

        sut = new BookServiceImpl(mockedBookRepository);
    }

    @Test
    void getBooks() {
        var result = sut.getBooks();
        assertEquals(getBookList(), result);
        verify(mockedBookRepository).findAll();
    }

    @Test
    void getBook() throws BookNotFoundException {
        var result = sut.getBook("Pre Build");
        assertEquals(getPrebuildBook(), result);
        verify(mockedBookRepository).getByBookId(anyString());

        assertThrows(BookNotFoundException.class, () -> sut.getBook("Not Build"));
    }

    @Test
    void addBook() throws DuplicateBookException {
        var result = sut.addBook(new Book());
        assertEquals(new Book(), result);
        verify(mockedBookRepository).getById(null);

        assertThrows(DuplicateBookException.class, () -> sut.addBook(getPrebuildBook()));
    }

    @Test
    void updateBook() throws BookNotFoundException {
        var result = sut.updateBook(getPrebuildBook());
        assertEquals(getPrebuildBook(), result);
        verify(mockedBookRepository).getById("ID1");

        assertThrows(BookNotFoundException.class, () -> sut.updateBook(new Book()));
    }

    @Test
    void removeBook() throws BookNotFoundException {
        assertTrue(sut.removeBook(getPrebuildBook()));
        verify(mockedBookRepository).getById("ID1");

        assertThrows(BookNotFoundException.class, () -> sut.removeBook(new Book()));
    }

    private List<Book> getBookList() {
        return new ArrayList<>(Arrays.asList(
                new Book("ID1", 1, 1, "BookID1"),
                new Book("ID2", 1, 2, "BookID2"),
                new Book("ID3", 1, 3, "BookID3"),
                new Book("ID4", 1, 4, "BookID4"),
                new Book("ID5", 1, 5, "BookID5")
        ));
    }

    private Book getPrebuildBook() {
        return new Book("ID1", 1, 1, "BookID1");
    }
}