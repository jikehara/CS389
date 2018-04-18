package services;

import configs.AppConfig;
import configs.TestDataConfig;
import models.User;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

@ContextConfiguration(classes = { AppConfig.class, TestDataConfig.class })
public class UserPersistenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Inject
	private UserService userService;

	// test being able to add a user
	@Test
	public void saveUserTest() {
		User u = new User();
		u.setUsername("test");
		userService.addUser(u);
		assertTrue(userService.userExists(u.getUsername()));
	}

	// the database does not allow data that is longer than 20 characters to be
	// added
	@Test(expected = javax.persistence.PersistenceException.class)
	public void addUsernameLong() {
		User user = new User();
		user.setUsername("user1WhichIsLongerThan20Characters");
		assertTrue(user.getUsername().length() > 20);
		userService.addUser(user);
		assertFalse(userService.userExists(user.getUsername()));
	}

	// the database does not allow data that is shorter than 3 characters to be
	// added
	@Test(expected = java.lang.AssertionError.class)
	public void addUsernameShort() {
		User user = new User();
		user.setUsername("u2");
		assertTrue(user.getUsername().length() < 3);
		userService.addUser(user);
		assertFalse(userService.userExists(user.getUsername()));
	}

	// cannot add a null user
	@Test
	public void addNullUser() {
		assertFalse(userService.addUser(null));
	}

	// cannot add a user with no username
	@Test
	public void notInitializedUser() {
		User user = new User();
		assertFalse(userService.addUser(user));
	}

	// the null user should not exist
	@Test
	public void nullUserExists() {
		assertFalse(userService.userExists(null));
	}

	@Test
	public void userDoesNotExist() {
		assertFalse(userService.userExists("user"));
	}

	// can add more than one user
	@Test
	public void addingMultipleUsers() {
		User user0 = new User();
		user0.setUsername("user0");
		userService.addUser(user0);
		assertTrue(userService.userExists(user0.getUsername()));

		User user1 = new User();
		user1.setUsername("user1");
		userService.addUser(user1);
		assertTrue(userService.userExists(user1.getUsername()));
	}

	@Test
	public void userDataNotExist() {
		assertNull(userService.getUserData("BobMarley"));
	}

	@Test
	public void nullUserDatat() {
		assertNull(userService.getUserData(null));
	}

	@Test
	public void userDataExists() {
		User user = new User();
		user.setUsername("user0");
		userService.addUser(user);
		assertNotNull(userService.getUserData(user.getUsername()));
	}
}