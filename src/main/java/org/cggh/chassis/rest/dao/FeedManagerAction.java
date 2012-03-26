/**
 * 
 */
package org.cggh.chassis.rest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.w3._2005.atom.Entry;
import org.w3._2005.atom.Feed;

/**
 * A SAM closure to avoid repeating a few key strokes. 
 * 
 * @author timp
 * @since 2011-12-13
 */
public abstract class FeedManagerAction {
  private static EntityManagerFactory emf;
  protected static EntityManager em;  
  
  public FeedManagerAction(EntityManagerFactory emfIn) { 
    emf = emfIn;
    em = emf.createEntityManager();
  }
  protected Feed runAsTransaction(Feed entry) { 
    Feed ret = null;
    if (!FeedManagerAction.em.getTransaction().isActive())
      em.getTransaction().begin();
    try {
      ret = action(entry);      
      em.getTransaction().commit();
    } catch (RuntimeException e) {
      if (em.isOpen() && em.getTransaction().isActive())
        em.getTransaction().rollback();
      e.printStackTrace();
      throw e;
    } finally {
      em.close();
    }
    return (ret);
  }
  public abstract Feed action(Feed entry);
}
