package pl.flashgoal.users.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    @Indexed(unique = true)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Indexed(unique = true)
    private String email;

    @NotBlank
    @Size(max = 30)
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    private Set<String> favouriteTags = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String password, Set<String> favouriteTags) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.favouriteTags = favouriteTags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<String> getFavouriteTags() {
        return favouriteTags;
    }

    public void setFavouriteTags(Set<String> favouriteTags) {
        this.favouriteTags = favouriteTags;
    }
}
