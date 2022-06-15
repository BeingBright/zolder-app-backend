package nl.brighton.zolder.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@CompoundIndex(name = "location-index", def = "{'row':1,'column':1,'location':1}", unique = true)
@Document(collection = "location")
public class Location {


  @Id
  private String id;
  private int row;
  private int column;
  private String location;
  private int bookNumber;

  public Location(String id, int row, int column, String location, int bookNumber) {
    this.id = id;
    this.row = row;
    this.column = column;
    this.location = location;
    this.bookNumber = bookNumber;
  }

  public Location() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getBookNumber() {
    return bookNumber;
  }

  public void setBookNumber(int bookNumber) {
    this.bookNumber = bookNumber;
  }
}
