package services;

import configs.AppConfig;
import configs.TestDataConfig;
import userValidation.User;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

@ContextConfiguration(classes = {AppConfig.class, TestDataConfig.class})
public class UserPersistenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Inject
    private UserService userPersist;

    @Test
    public void saveUserTest() {
        User u = new User();
        userPersist.addUser(u);
    }
}