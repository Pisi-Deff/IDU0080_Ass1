package ee.eerikmagi.ttu.idu0080.ass1.app;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ee.eerikmagi.ttu.idu0080.ass1.app.context.ConstraintViolationExceptionMapper;
import ee.eerikmagi.ttu.idu0080.ass1.app.context.ObjectMapperProvider;
import ee.eerikmagi.ttu.idu0080.ass1.app.context.SessionCreator;
import ee.eerikmagi.ttu.idu0080.ass1.app.context.SessionFactoryCreator;
import ee.eerikmagi.ttu.idu0080.ass1.dao.GamesDAO;

@ApplicationPath("ass1")
public class Ass1Application extends ResourceConfig {
	public Ass1Application() {
		// for JSON support
		register(ObjectMapperProvider.class);
		register(JacksonFeature.class);
		
		// exception handlers
		register(ConstraintViolationExceptionMapper.class);
		
		// hibernate
		register(new AbstractBinder() {
			@Override
			protected void configure() {
				// main hibernate magic
				bindFactory(SessionFactoryCreator.class)
					.to(SessionFactory.class).in(Singleton.class);
				bindFactory(SessionCreator.class)
					.to(Session.class).in(RequestScoped.class);
				
				// DAO
				bind(GamesDAO.class).to(GamesDAO.class).in(RequestScoped.class);
			}
		});
		
		// read resource classes
		packages("ee.eerikmagi.ttu.idu0080.ass1.rest");
	}
}
