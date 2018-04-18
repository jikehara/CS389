package controllers;

import services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.createAccount;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class CreateAccount extends Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateAccount.class);

    @Inject
    private UserService userService;

    public Result add() {
        return ok(createAccount.render(Form.form(User.class)));
    }

    public Result addUser() {
        log.debug("trying to add a new user");
        Form<User> form = Form.form(User.class).bindFromRequest();
        if (form.hasErrors()) {
            log.debug("form has errors, cannot add user");
            return badRequest(createAccount.render(form));
        }
        User user = form.get();
        if (userService.userExists(user.getUsername())) {
            log.info("username '{}' already exists, can't create account", user.getUsername());
            form.reject("username", "That username already exists, please enter a different username");
            return badRequest(createAccount.render(form));
        }
        log.info("adding {}", user.getUsername());
        userService.addUser(user);
        return redirect(controllers.routes.Login.login());
    }
}