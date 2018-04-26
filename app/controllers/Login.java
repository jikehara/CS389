package controllers;

import services.UserService;

import views.html.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.UserForm;
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
        log.debug("someone entering the login page");
        session().clear();
        return ok(login.render(Form.form(UserForm.class)));
    }

    public Result authenticate() {
        log.debug("checking authorization");
        Form<UserForm> form = Form.form(UserForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(login.render(form));
        }
        String username = form.get().getUsername().toUpperCase();
        log.debug("checking if '{}' exists", username);
        if (!(userService.userExists(username))) {
        	log.info("'{}' does not exist", username);
            form.reject("username", "That username doesn't exist");
            return badRequest(login.render(form));
        }        
        log.info("username exists. redirecting");
        session("username", username);
        log.debug("session=" + session("username"));
        return redirect(controllers.routes.Application.index()); // sends user to hello world
    }
}