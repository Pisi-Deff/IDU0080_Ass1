package ee.eerikmagi.ttu.idu0080.ass1.app.context;

import org.glassfish.hk2.api.Factory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import ee.eerikmagi.ttu.idu0080.ass1.model.db.Game;

public class SessionFactoryCreator implements Factory<SessionFactory> {
	@Override
	public SessionFactory provide() {
		Configuration configuration = new Configuration();
	    configuration.configure("hibernate.cfg.xml");
	    configuration.addAnnotatedClass(Game.class);
	    StandardServiceRegistryBuilder srBuilder = new StandardServiceRegistryBuilder();
	    srBuilder.applySettings(configuration.getProperties());
	    return configuration.buildSessionFactory(srBuilder.build());
	}

	@Override
	public void dispose(SessionFactory instance) {
		instance.close();
	}

}
