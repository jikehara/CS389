package models;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;


public class User {
    @Required(message = "Username is Required to create an account")
    @MinLength(value = 3)
    @MaxLength(value = 20)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}