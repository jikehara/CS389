package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class UserInfo {

    @Id
    @Column(name = "username")
    @Size(min = 3, max = 20)
    private String username;

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