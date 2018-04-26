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
		HighScores hs = getSingleUserHighScore(username);
		return (hs != null);
	}

	@Override
	public boolean canAddScore(UserInfo user, Long score) {
		if (user != null && !scoreExists(user.getUsername()) && user.getUsername() != null
				&& score > getSingleUserHighScore(user.getUsername()).getHighScore()) {
			return true;
		}
		return false;
	}

	@Override
	public HighScores getSingleUserHighScore(String username) {
		List<HighScores> hs = em
				.createQuery("SELECT a FROM HighScores a WHERE a.username = :username", HighScores.class)
				.setParameter("username", username).getResultList();
		if (hs.size() > 0) {
			return hs.get(0);
		}
		log.info("Trying to getHighScore for a {} that doesn't exist ", username);
		return null;
	}

}