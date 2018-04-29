package services;

import models.HighScores;
import models.ScoreForm;
import models.UserForm;
import models.UserInfo;
import views.html.index;

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
		HighScores hs = getSingleUserHighScore(username);
		return (hs != null);
	}

	@Override
	@Transactional
	public boolean addScore(UserForm user, ScoreForm score) {
		if (user == null || 
				user.getUsername() == null ||
				score == null
				) {
			log.debug("Invalid user or score.");
			return false;
		}
		HighScores hs = getSingleUserHighScore(user.getUsername());
		
		//null user -> new user
		if (hs == null) {
			hs = new HighScores();
			hs.setUsername(user.getUsername());
			hs.setHighScore((long) 0);
		}
		log.debug("Score being added for user: "+hs.getUsername());
		log.debug("New Score: "+score.getScore());
		if (score.getScore() <= hs.getHighScore()) {
			log.debug("Valid user, but score was not high enough to be a new high score.");
			return false;
		}		
		hs.setHighScore(score.getScore());
		em.persist(hs);
		return true;
	}

	@Override
	public HighScores getSingleUserHighScore(String username) {
		List<HighScores> hs = em
				.createQuery("SELECT a FROM HighScores a WHERE a.username = :username", HighScores.class)
				.setParameter("username", username).getResultList();
		if (!(hs.size() > 0)) {
			log.info("Trying to getHighScore for a {} that doesn't exist ", username);
			return null;
		}
		return hs.get(0);
	}

	public List<HighScores> getAllUserHighScores(String username) {
		return em
				.createQuery("SELECT * FROM HighScores ", HighScores.class).getResultList();
	}
}