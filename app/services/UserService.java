package services;

import models.User;
import models.UserInfo;

public interface UserService {
    boolean addUser(User user);

    boolean userExists(String username);

    UserInfo getUserData(String username);
}