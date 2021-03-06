package services;

import configs.AppConfig;
import configs.TestDataConfig;

import models.ScoreForm;
import models.UserForm;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

@ContextConfiguration(classes = { AppConfig.class, TestDataConfig.class })
public class ScorePersistenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Inject
	private ScoreService scoreService;

	// user/score add tests
	
	// test being able to add a user/score
	@Test
	public void addScoreTest() {
		ScoreForm s = new ScoreForm();
		UserForm u = new UserForm();
		u.setUsername("user0");
		s.setScore((long) 15);
		assertTrue(scoreService.addScore(u, s));
	}
	
	// cannot add a score that is lower than a previous score
	@Test
	public void addLowerScore() {
		ScoreForm s = new ScoreForm();
		UserForm u = new UserForm();
		u.setUsername("user0");
		s.setScore((long) 15); // score of 15
		assertTrue(scoreService.addScore(u, s));
		s.setScore((long) 10); // score of 10
		assertFalse(scoreService.addScore(u, s));
	}
	
	// can add a score that is the new highest score
	@Test
	public void addHigherScore() {
		ScoreForm s = new ScoreForm();
		UserForm u = new UserForm();
		u.setUsername("user0");
		s.setScore((long) 15); // score of 15
		assertTrue(scoreService.addScore(u, s));
		s.setScore((long) 100); // score of 100
		assertTrue(scoreService.addScore(u, s));
	}
	
	// cannot add an initial negative score
	@Test
	public void addNegativeScore() {
		ScoreForm s = new ScoreForm();
		UserForm u = new UserForm();
		u.setUsername("user0");
		s.setScore((long) -1);
		assertFalse(scoreService.addScore(u, s));
	}

	//Confirm that a new high score from a unique user works
	@Test
	public void addUniqueHigherScore() {
		ScoreForm s1 = new ScoreForm();
		UserForm u1 = new UserForm();
		ScoreForm s2 = new ScoreForm();
		UserForm u2 = new UserForm();
		u1.setUsername("user0");
		s1.setScore((long) 15); // score of 15
		u2.setUsername("userUnique");
		s2.setScore((long) 100); // score of 100
		assertTrue(scoreService.addScore(u1, s1));
		assertTrue(scoreService.addScore(u2, s2));
	}
	
	//Confirm that a second user can have a score lower than another user's score
	@Test
	public void addUniqueUserLowerScore() {
		ScoreForm s1 = new ScoreForm();
		UserForm u1 = new UserForm();
		ScoreForm s2 = new ScoreForm();
		UserForm u2 = new UserForm();
		u1.setUsername("user0");
		s1.setScore((long) 150); // user 0 score of 150
		u2.setUsername("user1");
		s2.setScore((long) 100); // user 1 score of 100
		assertTrue(scoreService.addScore(u1, s1));
		assertTrue(scoreService.addScore(u2, s2));
	}
}
