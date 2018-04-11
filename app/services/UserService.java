package services;

import models.UserInfo;
import userValidation.User;

public interface UserService {
    boolean addUser(User user);

    boolean userExists(String username);

    UserInfo getUserData(String username);
}