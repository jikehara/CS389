package services;

import models.HighScores;
import models.ScoreForm;
import models.UserForm;

public interface ScoreService {
    /**
     * Checks if the user with this username exists
     * @param username
     * @return True if matching username, false otherwise
     */
    boolean scoreExists(String username);

    /**
     * If user exists and score is greater than the previous score for this user, then
     * update the user's score. 
     * @param user
     * @param score
     * @return True if the score is a new high score and user is valid, false otherwise
     */
    boolean addScore(UserForm user, ScoreForm score);
    
    /**
     * Find the score associated with the param username, or null if no match to the username
     * exists within the Scores table.
     * @param username
     * @return HighScore (or null if no matching username)
     */
    HighScores getHighScore(String username);
}