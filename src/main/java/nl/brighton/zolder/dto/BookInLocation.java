package nl.brighton.zolder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "book-in-location")
public class BookInLocation {

  @Id
  private String id;
  @DBRef
  private String locationID;
  private int row;
  private int column;
  private String bookId;

  public boolean isEmpty() {
    return bookId.isEmpty() || bookId.isBlank();
  }
}
