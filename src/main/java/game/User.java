package game;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "USER_TABLE")
/***
 * A class representing a user.
 */
public class User {
    // TODO if there are more fields that the user might have many of which are not required
    // Maybe it would be nice idea to create a UserBuilder class

    private @Id
    @GeneratedValue
    Long id;
    private String name;
    private UserRole role;
    private String username;
    private String encodedPassword;

    public User(String name, String username, String password, UserRole role) {
        this.name = name;
        this.username = username;
        this.encodedPassword = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
}
