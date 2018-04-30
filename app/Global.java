import configs.AppConfig;
import configs.DataConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {

	private ApplicationContext context;

	// sets up app configuration and data configuration at compile
	@Override
	public void onStart(Application app) {
		super.onStart(app);
		context = new AnnotationConfigApplicationContext(AppConfig.class, DataConfig.class);
	}

	@Override
	public <A> A getControllerInstance(Class<A> clazz) {
		return context.getBean(clazz);
	}
}
