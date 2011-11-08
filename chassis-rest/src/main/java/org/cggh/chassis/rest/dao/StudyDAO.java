package org.cggh.chassis.rest.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.w3._2005.atom.Entry;

public class StudyDAO {
	// http://static.springsource.org/spring/docs/3.0.5.RELEASE/reference/orm.html#orm-jpa

	private EntityManagerFactory emf;

	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

  public Entry getEntry(String id) {
    final EntityManager em = emf.createEntityManager();
    final Entry beta = em.find(Entry.class, id);
    em.close();
    return beta;
  }

  @SuppressWarnings("unchecked")
  public Collection<Entry> getAll() {
    final EntityManager em = emf.createEntityManager();
    Query query = em.createQuery("SELECT e FROM Entry e");
    Collection<Entry> resultsList = (Collection<Entry>)query.getResultList();
    em.close();
    return resultsList;
  }

	public void saveEntry(Entry entry) {
		final EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
      em.persist(entry);
      em.getTransaction().commit();
    } catch (RuntimeException e) { 
      if (em.isOpen() &&  em.getTransaction().isActive())
        em.getTransaction().rollback();
      throw e;
		} finally { 
		  em.close();
		}
	}

	public void updateEntry(String id, Entry entry) {
		final EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
    try {	
		  em.merge(entry);
	    em.getTransaction().commit();
    } catch (RuntimeException e) { 
      if (em.isOpen() &&  em.getTransaction().isActive())
        em.getTransaction().rollback();
      throw e;
    } finally { 
      em.close();
    }
	}

	public boolean remove(String id)  {
		final EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Entry entry = em.find(Entry.class, id);
		boolean found = false;
		if (entry != null) {
		  found = true;
      try {
			  em.remove(entry);
	      em.getTransaction().commit();
		  } catch (RuntimeException e) { 
        if (em.isOpen() &&  em.getTransaction().isActive())
          em.getTransaction().rollback();
        throw e;
      } finally { 
        em.close();
      }
		} else { 
      em.close();		  
		}
		return found;
	}
}
