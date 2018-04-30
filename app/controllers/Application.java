package controllers;

import models.HighScores;
import models.ScoreForm;
import models.UserForm;
import services.ScoreService;
import services.ScoreServiceImplementation;
import services.UserServiceImplementation;

import play.data.Form;

import play.mvc.Controller;
import play.mvc.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import views.html.index;
import views.html.game;
import views.html.scoreError;

@Named
public class Application extends Controller {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Inject
	private ScoreService scoreService;

	/**
	 * Checks if session has a user, then renders the 'index' page
	 * 
	 * @return Result, renders the home page
	 */
	public Result index() {
		if (!isLoggedIn()) {
			redirect(routes.Login.login());
		}
		return ok(index.render("hello, world",
				scoreService.getAllUserHighScores()));
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

	/**
	 * Takes the submitted form data and creates a high score using a user session
	 * and either succeeds or reloads the form with error
	 * 
	 * @return either a 200 or 400 code with a redirect
	 */
	@Transactional
	public Result addHighScore() {
		logger.debug("Adding a high score with session user: " + session("username"));
		Form<models.ScoreForm> form = Form.form(models.ScoreForm.class).bindFromRequest();
		if (form.hasErrors()) {
			logger.debug("Failed to add a high score, form has errors. No value was passed in form");
			if (form.get().getScore() == null) {
				logger.debug("No value was passed in form");
			}
			return badRequest(index.render("hello, world",
					scoreService.getAllUserHighScores()));
		}
		ScoreForm score = form.get();
		UserForm user = new UserForm();
		user.setUsername(session("username"));
		logger.debug("New Score is " + score.getScore() + " for user " + user.getUsername());

		if (!(scoreService.addScore(user, score))) {
			logger.debug("Could not persist new high score.");

			return badRequest(scoreError.render("score is not a Highscore",
					scoreService.getAllUserHighScores(), "Your score was not a HighScore, play again"));
		}
		// manage.remove(highestScoreForUser);
		logger.debug("Added a High Score!");
		return ok(views.html.scoreYes.render("score is a Highscore",
				scoreService.getAllUserHighScores(), "Congrats, you set a new highscore, try to beat it!"));
	}

	/**
	 * fetches stored scores from database and passes them as JSON
	 * 
	 * @return a 200 ok code and passes the JSON scores
	 */
	public Result getHighScores() {
		List<HighScores> scores = scoreService.getAllUserHighScores();
		return ok(play.libs.Json.toJson(scores));
	}

	private boolean isLoggedIn() {
		if (session("username") == null) {
			logger.debug("bad username, page unavailable");
			return false;
		}
		return true;
	}
}