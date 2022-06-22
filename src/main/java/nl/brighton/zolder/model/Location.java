package nl.brighton.zolder.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "books")
@Document(collection = "location")
@CompoundIndex(name = "location-index", def = "{'buildingLocation':1,'inventoryLocation':1}", unique = true)
public class Location {

  @Id
  private String id;
  private int rowCount;
  private int columnCount;
  private String buildingLocation;
  private String inventoryLocation;
  @DBRef
  private List<Book> books = new ArrayList<>();

}
