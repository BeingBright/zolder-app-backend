package nl.brighton.zolder.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "user")
@CompoundIndex(name = "username-index", def = "{'username':1}", unique = true)
public class User {

    @Id
    private String id;
    @EqualsAndHashCode.Include
    private String username;
    @EqualsAndHashCode.Include
    private String password;
    private boolean isActive = false;
    private UserRoleType role;
    @Transient
    private boolean rememberMe = false;

    public boolean hasSameID(User user) {
        return this.id.equals(user.id);
    }

    public void clearPassword() {
        this.password = null;
    }

    public User(String id, String username, String password, boolean isActive, UserRoleType role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.role = role;
    }
}

