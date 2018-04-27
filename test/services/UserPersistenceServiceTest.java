package services;

import configs.AppConfig;
import configs.TestDataConfig;
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
public class UserPersistenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Inject
	private UserService userService;

	// test being able to add a user
	@Test
	public void saveUserTest() {
		UserForm u = new UserForm();
		u.setUsername("test");
		userService.addUser(u);
		assertTrue(userService.userExists(u.getUsername()));
	}

	// the database does not allow data that is longer than 20 characters to be
	// added
	@Test
	public void addUsernameLong() {
		UserForm user = new UserForm();
		user.setUsername("user1WhichIsLongerThan20Characters");
		assertTrue(user.getUsername().length() > 20);
		assertFalse("Should be false because the user should be too long to be added", userService.addUser(user));
	}

	// the database does not allow data that is shorter than 3 characters to be
	// added
	@Test
	public void addUsernameShort() {
		UserForm user = new UserForm();
		user.setUsername("u2");
		assertTrue(user.getUsername().length() < 3);
		assertFalse("Should be false because the user should be too short to be added", userService.addUser(user));
	}

	// cannot add a null user
	@Test
	public void addNullUser() {
		assertFalse(userService.addUser(null));
	}

	// can add more than one user
	@Test
	public void addingMultipleUsers() {
		UserForm user0 = new UserForm();
		user0.setUsername("user0");
		userService.addUser(user0);		

		UserForm user1 = new UserForm();
		user1.setUsername("user1");
		userService.addUser(user1);
		
		assertTrue(userService.userExists(user0.getUsername()));
		assertTrue(userService.userExists(user1.getUsername()));
	}

	// Adding a user doesn't add other users
	@Test
	public void addingSpecificUsers() {
		UserForm user0 = new UserForm();
		user0.setUsername("user0");
		userService.addUser(user0);
		assertTrue(userService.userExists(user0.getUsername()));

		assertFalse(userService.userExists("user1"));
	}
		
	// cannot add duplicate users
	@Test
	public void addingDuplicateUsers() {
		UserForm user0 = new UserForm();
		user0.setUsername("user0");
		userService.addUser(user0);
		assertTrue(userService.userExists(user0.getUsername()));

		UserForm user1 = new UserForm();
		user1.setUsername("user0");
		assertFalse("Should be false because the user does not have a unique username",userService.addUser(user1));
	}
	
	// cannot add the same user twice
	@Test
	public void addingUserTwice() {
		UserForm user0 = new UserForm();
		user0.setUsername("user0");
		userService.addUser(user0);
		assertTrue(userService.userExists(user0.getUsername()));
		assertFalse(userService.addUser(user0));
		
	}
		
	// cannot add a user with no username
	@Test
	public void notInitializedUser() {
		UserForm user = new UserForm();
		assertFalse(userService.addUser(user));
	}

	// the null user should not exist
	@Test
	public void nullUserExists() {
		assertFalse(userService.userExists(null));
	}
	
	@Test
	public void blankUserExists() {
		assertFalse(userService.userExists(""));
	}

	@Test
	public void userDoesNotExist() {
		assertFalse(userService.userExists("user"));
	}

	@Test
	public void getUserDataNotExist() {
		assertNull(userService.getUserData("BobMarley"));
	}

	@Test
	public void nullUserDatat() {
		assertNull(userService.getUserData(null));
	}

	@Test
	public void getUserDataExists() {
		UserForm user = new UserForm();
		user.setUsername("user0");
		userService.addUser(user);
		assertNotNull(userService.getUserData(user.getUsername()));
	}
}