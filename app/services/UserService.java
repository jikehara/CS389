package services;

import models.UserForm;
import models.UserInfo;

public interface UserService {
	/**
	 * Checks a user to see if it is null or matches the username of a different user.
	 * Will persist a new user to the entity manager
	 * @param user
	 * @return false if the user is null or matches an existing user, otherwise true
	 */
    boolean addUser(UserForm user);

    /**
     * Checks if the user with this username exists
     * @param username
     * @return True if matching username, false otherwise
     */
    boolean userExists(String username);

    /**
     * Find a user with this username and return the userinfo, or return null if no matching
     * username can be found
     * @param username
     * @return UserInfo (or null if no matching username)
     */
    UserInfo getUserData(String username);
}