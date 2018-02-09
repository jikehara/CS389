package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	public static Result index() {
        return ok(index.render("hello, world", play.data.Form.form(models.Task.class)));
    }

	@play.db.jpa.Transactional
	public static Result addTask() {
        play.data.Form<models.Task> form = play.data.Form.form(models.Task.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(index.render("hello, world", form));
        } else {
            models.Task task = form.get();
            play.db.jpa.JPA.em().persist(task);
            return redirect(routes.Application.index());
        }
    }
    
    public static Result getTasks() {
        java.util.List<models.Task> tasks = new java.util.ArrayList<>();
        return ok(play.libs.Json.toJson(tasks));
    }    
    
}
