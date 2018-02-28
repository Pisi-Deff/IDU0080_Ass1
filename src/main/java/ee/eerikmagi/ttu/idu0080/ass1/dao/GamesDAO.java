package ee.eerikmagi.ttu.idu0080.ass1.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ee.eerikmagi.ttu.idu0080.ass1.model.db.Game;

public class GamesDAO {
	@Inject
	private Provider<Session> sessionProvider;
	
	public List<Game> list() {
		Session s = session();
		CriteriaBuilder builder = s.getCriteriaBuilder();
		CriteriaQuery<Game> crit = builder.createQuery(Game.class);
		Root<Game> root = crit.from(Game.class);
		crit.select(root);
		return s.createQuery(crit).getResultList();
	}
	
	public Game get(long lID) {
		return session().get(Game.class, lID);
	}
	
	public List<Game> findByPartialName(String sName) {
		Session s = session();
		CriteriaBuilder builder = s.getCriteriaBuilder();
		CriteriaQuery<Game> crit = builder.createQuery(Game.class);
		Root<Game> root = crit.from(Game.class);
		crit.where(
			builder.like(
				builder.lower(root.get("sName")),
				sName.toLowerCase() + "%"
			)
		);
		return s.createQuery(crit).getResultList();
	}
	
	public void save(Game game) {
		Session s = session();
		Transaction tx = s.beginTransaction();
		s.saveOrUpdate(game);
		tx.commit();
	}
	
	public void delete(long lID) {
		Session s = session();
		Transaction tx = s.beginTransaction();
		
		Game g = s.get(Game.class, lID);
		if (g != null) {
			s.remove(g);
		}
		
		tx.commit();
	}
	
	private Session session() {
		return sessionProvider.get();
	}
}
