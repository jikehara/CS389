package controllers;

import models.Task;
import play.data.Form;

import play.mvc.Controller;
import play.mvc.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import views.html.index;

@Named
public class Application extends Controller {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	@PersistenceContext
	private EntityManager manage;
	
	public Result index() {
        return ok(index.render("hello, world", Form.form(models.Task.class)));
    }

	@Transactional
	public Result addTask() {
		logger.info("Adding a task...");
        play.data.Form<models.Task> form = play.data.Form.form(models.Task.class).bindFromRequest();
        if (form.hasErrors()) {
        	logger.info("Failed to add a task, bad form.");
            return badRequest(index.render("hello, world", form));
        } else {
            models.Task task = form.get();
            manage.persist(task);
            logger.info("Added a task!");
            return redirect(routes.Application.index());            
        }
    }
    
	
    public Result getTasks() {
		List<Task> tasks = manage.createQuery("FROM Task", Task.class).getResultList();
        return ok(play.libs.Json.toJson(tasks));
    }    
    
}
