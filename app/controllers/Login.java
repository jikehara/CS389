package controllers;

import services.UserService;

import views.html.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class Login extends Controller {
    private static final Logger log = LoggerFactory.getLogger(Login.class);

    @Inject
    private UserService userService;

    public Result login() {
        log.info("someone entering the login page");
        session().clear();
        return ok(login.render(Form.form(User.class)));
    }

    public Result authenticate() {
        log.info("checking authorization");
        Form<User> form = Form.form(User.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(login.render(form));
        }
        String username = form.get().getUsername();
        log.info("checking if '{}' exists", username);
        if (userService.userExists(username)) {
            log.info("username exists. redirecting to theButton()");
            session("username", username);
            log.debug("session=" + session("username"));
            return redirect(controllers.routes.Application.index()); // sends user to hello world
        }
        log.info("'{}' does not exist", username);
        form.reject("username", "That username doesn't exist");
        return badRequest(login.render(form));
    }
}