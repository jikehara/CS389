package services;

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
public class UserServiceImplementation implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImplementation.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public boolean addUser(UserForm user) {
        if (user != null && !userExists(user.getUsername()) && user.getUsername() != null) {
            UserInfo newUser = new UserInfo();
            newUser.setUsername(user.getUsername());
            em.persist(newUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean userExists(String username) {
        UserInfo ui = getUserData(username);
        return (ui != null);
    }

    @Override
    public UserInfo getUserData(String username) {
        List<UserInfo> ui = em.createQuery("SELECT a FROM UserInfo a WHERE a.username = :username", UserInfo.class)
                              .setParameter("username", username)
                              .getResultList();
        if (ui.size() > 0) {
            return ui.get(0);
        }
        log.info("Trying to getUserInfo for a {} that doesn't exist ", username);
        return null;
    }
}