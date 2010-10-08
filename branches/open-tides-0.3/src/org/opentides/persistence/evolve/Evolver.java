package org.opentides.persistence.evolve;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

public class Evolver {
	// the entity manager
	@PersistenceContext
    private EntityManager em;
	
	@Transactional
	public int executeJpqlUpdate(String query) {
		Query q = em.createQuery(query);
		return q.executeUpdate();
	}
	
	@Transactional
	public int executeSqlUpdate(String query) {
		Query q = em.createNativeQuery(query);
		return q.executeUpdate();
	}

	public final void setEntityManager(EntityManager em) {
        this.em = em;
    }

}
