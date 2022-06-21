package nl.brighton.zolder.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.brighton.zolder.dto.types.BuildingLocationType;
import nl.brighton.zolder.dto.types.InventoryLocationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
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
  private BuildingLocationType buildingLocation;
  private InventoryLocationType inventoryLocation;
  private List<Book> books=new ArrayList<>();

  public Location(BuildingLocationType buildingLocation, InventoryLocationType inventoryLocation) {
    this.buildingLocation = buildingLocation;
    this.inventoryLocation = inventoryLocation;
    this.id = null;
    this.rowCount = 1;
    this.columnCount = 1;
    this.books = new ArrayList<>();
  }
}
