package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserInfo {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "username", nullable = false, length=20)
    private String username;

    public Long getId() {
        return id;
    }

    /**
     * Sets the primary key id to the param id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the username from the object
     * @return String username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username to the param username
     * @param String username
     */
    public void setUsername(String username) {
        this.username = username.toUpperCase();
    }
    
}