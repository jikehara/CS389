package services;

import models.HighScores;
import models.ScoreForm;
import models.UserForm;
import models.UserInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class ScoreServiceImplementation implements ScoreService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImplementation.class);

    @PersistenceContext
    private EntityManager em;

	@Override
	public boolean scoreExists(String username) {
		HighScores hs = getHighScore(username);
        return (hs != null);
	}

	@Override
	public boolean addScore(UserInfo user, ScoreForm score) {
		if (user != null 
				&& !scoreExists(user.getUsername()) 
				&& user.getUsername() != null
				&& score.getScore() > getHighScore(user.getUsername()).getHighScore()
			) {
            HighScores newScore = new HighScores();
            newScore.setUsername(user.getUsername());
            newScore.setHighScore(score.getScore());
            em.persist(newScore);
            return true;
        }
        return false;
	}

	@Override
    public HighScores getHighScore(String username) {
        List<HighScores> hs = em.createQuery("SELECT a FROM HighScores a WHERE a.username = :username", HighScores.class)
                              .setParameter("username", username)
                              .getResultList();
        if (hs.size() > 0) {
            return hs.get(0);
        }
        log.info("Trying to getHighScore for a {} that doesn't exist ", username);
        return null;
    }

    
}