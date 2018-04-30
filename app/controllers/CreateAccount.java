package controllers;

import services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.UserForm;
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
		return ok(createAccount.render(Form.form(UserForm.class)));
	}

	public Result addUser() {
		log.debug("trying to add a new user");
		Form<UserForm> form = Form.form(UserForm.class).bindFromRequest();
		if (form.hasErrors()) {
			log.debug("form has errors, cannot add user");
			return badRequest(createAccount.render(form));
		}
		UserForm user = form.get();
		user.setUsername(user.getUsername().trim());
		if (user.getUsername().length() < 3) {
			form.reject("username",
					"Username needs to be between 3 and 20 characters, without leading or trailing whitespace.");
			return badRequest(createAccount.render(form));
		}
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