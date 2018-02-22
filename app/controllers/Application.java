package controllers;

import models.Task;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import views.html.index;

public class Application extends Controller {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static Result index() {
        return ok(index.render("hello, world", Form.form(models.Task.class)));
    }

	@Transactional
	public static Result addTask() {
		logger.info("Adding a task...");
        play.data.Form<models.Task> form = play.data.Form.form(models.Task.class).bindFromRequest();
        if (form.hasErrors()) {
        	logger.info("Failed to add a task, bad form.");
            return badRequest(index.render("hello, world", form));
        } else {
            models.Task task = form.get();
            JPA.em().persist(task);
            logger.info("Added a task!");
            return redirect(routes.Application.index());            
        }
    }
    
	@Transactional
    public static Result getTasks() {
		List<Task> tasks = JPA.em().createQuery("FROM Task", Task.class).getResultList();
        return ok(play.libs.Json.toJson(tasks));
    }    
    
}
