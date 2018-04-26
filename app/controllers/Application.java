package controllers;

import models.HighScores;
import models.ScoreForm;

import services.ScoreServiceImplementation;
import services.UserServiceImplementation;

import play.data.Form;

import play.mvc.Controller;
import play.mvc.Result;

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

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	private UserServiceImplementation userService;
	private ScoreServiceImplementation scoreService;

	@PersistenceContext
	private EntityManager manage;
	
	/**
	 * Checks if session has a user, then renders the 'index' page
	 * @return Result, renders the home page
	 */
	public Result index() {
	    if (!isLoggedIn()) {
	    	redirect(routes.Login.login());
	    }
        return ok(index.render("hello, world", manage.createQuery("FROM HighScores ORDER BY Score DESC", HighScores.class).getResultList()));
    }

	/**
	 * Checks if session has a user, then renders the 'game' page
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
	 * @return either a 200 or 400 code with a redirect 
	 */
	@Transactional
    public Result addHighScore() {
    	logger.debug("Adding a high score with session user: "+session("username"));
    	Form<models.ScoreForm> form = Form.form(models.ScoreForm.class).bindFromRequest();
        if (form.hasErrors()) {
			logger.debug("Failed to add a high score, form has errors. No value was passed in form");
        	if(form.get().getScore()==null) {
				logger.debug("No value was passed in form");
			}
            return badRequest(index.render("hello, world", manage.createQuery("FROM HighScores ORDER BY Score DESC", HighScores.class).getResultList()));
        }
        HighScores score = new HighScores();        
        score.setHighScore(form.get().getScore());        
        score.setUsername(session("username"));
        logger.debug("Score is "+score.getHighScore()+" for user "+score.getUsername());
        
        //validate score based on user and score
        List<HighScores> scores = manage.createQuery("FROM HighScores h WHERE h.username = :name ORDER BY h.highScore DESC", HighScores.class)
        		.setParameter("name", score.getUsername())
        		.getResultList();
        HighScores highestScoreForUser = null;
        for (HighScores h : scores) {
        	if (score.getHighScore() < h.getHighScore()) {
        		logger.info("Score was not high enough to be this user's new high score ");
                return badRequest(index.render("hello, world", manage.createQuery("FROM HighScores ORDER BY Score DESC", HighScores.class).getResultList()));
    		}
    		highestScoreForUser = h;
        }
        if (highestScoreForUser == null) {
        	highestScoreForUser = new HighScores();
        	highestScoreForUser.setUsername(score.getUsername());
        }
        highestScoreForUser.setHighScore(score.getHighScore());
        
        manage.persist(highestScoreForUser);
        //manage.remove(highestScoreForUser);
        logger.debug("Added a High Score!");
        return redirect(routes.Application.index()); 
    }

	/**
	 * fetches stored scores from database and passes them as JSON
	 * @return a 200 ok code and passes the JSON scores
	 */
	public Result getHighScores() {
		List<HighScores> scores = manage.createQuery("FROM HighScores ORDER BY Score DESC", HighScores.class).getResultList();
        return ok(play.libs.Json.toJson(scores));
    }  
    
    private boolean isLoggedIn() {
    	if (session("username")==null) {
	    	logger.debug("bad username, page unavailable");
	    	return false;
	    }
    	return true;
    }
}