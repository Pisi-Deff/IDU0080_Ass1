package ee.eerikmagi.ttu.idu0080.ass1.app.context;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SessionCreator implements Factory<Session> {
	private SessionFactory sf;
	
	@Inject
	public SessionCreator(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Session provide() {
		return this.sf.openSession();
	}

	@Override
	public void dispose(Session instance) {
		if (instance.isOpen()) {
			instance.close();
		}
	}

}
