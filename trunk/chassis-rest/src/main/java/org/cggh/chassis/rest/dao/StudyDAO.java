package org.cggh.chassis.rest.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;

import org.w3._2005.atom.Entry;

public class StudyDAO {
	// http://static.springsource.org/spring/docs/3.0.5.RELEASE/reference/orm.html#orm-jpa

	private EntityManagerFactory emf;

	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public void saveEntry(Entry alpha) throws JAXBException {

		final EntityManager saveManager = emf.createEntityManager();
		saveManager.getTransaction().begin();
		saveManager.persist(alpha);
		saveManager.getTransaction().commit();
		saveManager.close();

	}

	public Entry getEntry(String id) throws JAXBException {
		final EntityManager loadManager = emf.createEntityManager();

		final Entry beta = loadManager.find(Entry.class, id);
		loadManager.close();
		return beta;
	}

	@SuppressWarnings("unchecked")
  public Collection<Entry> getAll() {
		final EntityManager loadManager = emf.createEntityManager();
		Query query = loadManager.createQuery("SELECT e FROM Entry e");
		Collection<Entry> ret = (Collection<Entry>) query.getResultList();
		loadManager.close();
		return (ret);
	}

	public void updateEntry(String id, Entry entry) {
		final EntityManager saveManager = emf.createEntityManager();

		saveManager.getTransaction().begin();
		
		saveManager.merge(entry);

		saveManager.getTransaction().commit();
		saveManager.close();
	}

	public void remove(String id) {
		final EntityManager saveManager = emf.createEntityManager();

		saveManager.getTransaction().begin();
		Entry beta = saveManager.find(Entry.class, id);
		if (null != beta) {
			saveManager.remove(beta);
		}
		
		saveManager.getTransaction().commit();
		saveManager.close();
		
	}
}
