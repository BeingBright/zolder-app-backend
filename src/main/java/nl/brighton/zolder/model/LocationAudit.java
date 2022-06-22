package nl.brighton.zolder.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.brighton.zolder.model.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = "location-audit")
public class LocationAudit {

  @Id
  private String id;
  @DBRef
  private Location location;
  @DBRef
  private Book bookInLocation;
  private String bookId;
  @DBRef
  private User user;
  private Date timestamp;

}
