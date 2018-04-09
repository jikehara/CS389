package userValidation;

import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;

public class LoginInfo {
    @Required(message = "Username is required to login")
    @MaxLength(value = 20)
    @MinLength(value = 3)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String input) {
        this.username = input;
    }
}