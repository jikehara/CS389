package controllers;

import models.HighScores;

import play.data.Form;

import play.mvc.Controller;
import play.mvc.Result;
import services.ScoreService;
import services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import views.html.index;
import views.html.game;

@Named
public class Application extends Controller {

	private ScoreService scoreService;
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@PersistenceContext
	private EntityManager manage;

	/**
	 * Checks if session has a user, then renders the 'index' page
	 * 
	 * @return Result, renders the home page
	 */
	public Result index() {
		if (!isLoggedIn()) {
			redirect(routes.Login.login());
		}
		return ok(index.render("hello, world", Form.form(models.ScoreForm.class)));
	}

	/**
	 * Checks if session has a user, then renders the 'game' page
	 * 
	 * @return renders page for the game
	 */
	public Result game() {
		if (!isLoggedIn()) {
			redirect(routes.Login.login());
		}
		return ok(game.render());
	}

	@Transactional
	public Result addHighScore() {
		logger.debug("Adding a high score with session user: " + session("username"));
		Form<models.ScoreForm> form = Form.form(models.ScoreForm.class).bindFromRequest();
		if (form.hasErrors()) {
			logger.debug("Failed to add a high score, form has errors.");
			return badRequest(index.render("hello, world", form));
		}
		HighScores score = new HighScores();
		score.setHighScore(form.get().getScore());
//		logger.debug("user data for this score: " +userService.getUserData(score.getUsername()));
		score.setUsername(session("username"));
//		if (!(scoreService.addScore(userService.getUserData(score.getUsername()), form.get()))) {
//			logger.debug("Failed to add a high score, User or score invalid");
//			form.reject("Invalid user, or valid user with an invalid score");
//			return badRequest(index.render("hello, world", form));
//		}
		manage.persist(score);
		logger.debug("Added a High Score!");
		return redirect(routes.Application.index());
	}

	public Result getHighScores() {
		List<HighScores> scores = manage.createQuery("FROM HighScores ORDER By scores DESC", HighScores.class).getResultList();
		return ok(play.libs.Json.toJson(scores));
	}

	private boolean isLoggedIn() {
		logger.debug("session=" + session("username"));
		if (session("username") == null) {
			logger.debug("bad username, page unavailable");
			return false;
		}
		return true;
	}
}
