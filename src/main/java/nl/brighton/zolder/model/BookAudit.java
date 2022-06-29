package nl.brighton.zolder.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = "book-audit")
@CompoundIndex(name = "book-index", def = "{'bookInLocation':1}")

public class BookAudit {

  @Id
  private String id;
  @DBRef
  private Book bookInLocation;
  private String bookId;
  private String username;
  private Date timestamp;

  public BookAudit(Book bookInLocation, String bookId, String username, Date timestamp) {
    this.bookInLocation = bookInLocation;
    this.bookId = bookId;
    this.username = username;
    this.timestamp = timestamp;
  }
}
