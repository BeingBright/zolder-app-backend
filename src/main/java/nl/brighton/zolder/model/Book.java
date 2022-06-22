package nl.brighton.zolder.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"Id", "bookId"})
@Document(collection = "book")
public class Book {

    @Id
    private String Id;
    private int row;
    private int column;
    private String bookId;

    public boolean isEmpty() {
        return bookId.isEmpty() || bookId.isBlank();
    }

}

