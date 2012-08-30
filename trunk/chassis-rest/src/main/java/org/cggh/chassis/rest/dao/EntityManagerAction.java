/**
 * 
 */
package org.cggh.chassis.rest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.w3._2005.atom.Entry;

/**
 * A SAM closure to avoid repeating a few key strokes. 
 * 
 * @author timp
 * @since 2011-12-13
 */
public abstract class EntityManagerAction {
  private EntityManagerFactory emf;
  protected EntityManager em;  
  
  public EntityManagerAction(EntityManagerFactory emfIn) { 
    emf = emfIn;
    em = emf.createEntityManager();
  }
  protected Entry runAsTransaction(Entry entry) { 
    Entry ret = null;
    if (!em.getTransaction().isActive())
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
  public abstract Entry action(Entry entry);
}
