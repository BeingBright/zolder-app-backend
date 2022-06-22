package nl.brighton.zolder.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
@Document(collection = "location-audit")
public class LocationAudit {

  @Id
  private String id;
  @DBRef
  private String locationID;
  @DBRef
  private String bookInLocationID;
  private int bookNumber;
  @DBRef
  private String userID;
  private Date timestamp;

}
