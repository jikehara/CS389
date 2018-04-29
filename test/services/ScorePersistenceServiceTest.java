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
	@Inject
	private UserService userService;

	// user add tests
	// test being able to add a user/score
	@Test
	public void addScoreTest() {
		ScoreForm s = new ScoreForm();
		UserForm u = new UserForm();
		s.setScore((long) 15);
		assertTrue(scoreService.addScore(u, s));
	}
	
}