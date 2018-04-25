package controllers;

import models.HighScores;

import models.ScoreForm;
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
        return ok(index.render("hello, world", Form.form(models.ScoreForm.class)));
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
    
    @Transactional
    public Result addHighScore() {
    	logger.debug("Adding a high score with session user: "+session("username"));
    	Form<models.ScoreForm> form = Form.form(models.ScoreForm.class).bindFromRequest();
        if (form.hasErrors()) {
        	logger.debug("Failed to add a high score, form has errors. expected a number, we got");
            return badRequest(index.render("hello, world", form));
        }
        HighScores score = new HighScores();        
        score.setHighScore(form.get().getScore());
        logger.debug("Score is "+score.getHighScore());
        score.setUsername(session("username"));
        manage.persist(score);
        logger.debug("Added a High Score!");
        return redirect(routes.Application.index()); 
    }
    
    public Result getHighScores() {
		List<HighScores> scores = manage.createQuery("FROM HighScores", HighScores.class).getResultList();
        return ok(play.libs.Json.toJson(scores));
    }  
    
    private boolean isLoggedIn() {
	    logger.debug("session=" + session("username"));
    	if (session("username")==null) {
	    	logger.debug("bad username, page unavailable");
	    	return false;
	    }
    	return true;
    }
}
