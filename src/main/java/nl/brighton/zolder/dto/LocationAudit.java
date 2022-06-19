package nl.brighton.zolder.dto;

import java.util.Date;
import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "location-audit")
public class LocationAudit {

    private Location location;
    private int bookNumber;
    private String username;
    private Date timestamp;

    public LocationAudit() {
    }

    public LocationAudit(Location location, int bookNumber, String username, Date timestamp) {
        this.location = location;
        this.bookNumber = bookNumber;
        this.username = username;
        this.timestamp = timestamp;
    }

    public LocationAudit(Location location, int bookNumber, String username) {
        this.location = location;
        this.bookNumber = bookNumber;
        this.username = username;
        this.timestamp = new Date();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationAudit that = (LocationAudit) o;
        return bookNumber == that.bookNumber && location.equals(that.location) && username.equals(that.username) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, bookNumber, username, timestamp);
    }
}
