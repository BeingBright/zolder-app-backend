package nl.brighton.zolder.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"Id"})
public class Book {

  private String Id;
  private int row;
  private int column;

  public boolean isEmpty() {
    return Id.isEmpty() || Id.isBlank();
  }
}
