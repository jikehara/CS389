package services;

import models.HighScores;
import models.ScoreForm;
import models.UserInfo;

public interface ScoreService {
    /**
     * Checks if the user with this username exists
     * @param username
     * @return True if matching username, false otherwise
     */
    boolean scoreExists(String username);

    /**
     * Check if a score can be added if it is a new user or a duplicate user with a new high score
     * @param user
     * @param score
     * @return True if the score is a new high score and user is valid, false otherwise
     */
    boolean canAddScore(UserInfo user, Long score);
    
    /**
     * Find the score associated with the param username, or null if no match to the username
     * exists within the Scores table.
     * @param username
     * @return HighScore (or null if no matching username)
     */
    HighScores getSingleUserHighScore(String username);
}