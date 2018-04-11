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

    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * String getUsername is used to get the private String username
     * @return String username
     */
    public String getUsername() {
        return username;
    }

    /**
     * void setUsername takes a string argument and sets the private variable
     * "username" to the value of the passed in string
     * @param String username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
}