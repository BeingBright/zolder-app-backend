package nl.brighton.zolder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.EqualsAndHashCode.Include;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "book")
public class Book {

    @Id
    private String id;
    @Include
    private int row;
    @Include
    private int column;
    private String bookId;

    @Transient
    public boolean isEmpty() {
        return bookId.isEmpty() || bookId.isBlank();
    }

    public Book(int row, int column, String bookId) {
        this.row = row;
        this.column = column;
        this.bookId = bookId;
    }
}

