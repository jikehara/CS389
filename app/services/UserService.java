package services;

import models.User;
import models.UserInfo;

public interface UserService {
	/**
	 * Checks a user to see if it is null or matches the username of a different user
	 * @param user
	 * @return false if the user is null or matches an existing user, otherwise true
	 */
    boolean addUser(User user);

    /**
     * 
     * @param username
     * @return
     */
    boolean userExists(String username);

    /**
     * 
     * @param username
     * @return
     */
    UserInfo getUserData(String username);
}