/**
 * 
 */
package org.cggh.chassis.rest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * A SAM closure to avoid repeating a few key strokes. 
 * 
 * @author timp
 * @since 2011-12-13
 */
public abstract class EntityManagerAction {
  private static EntityManagerFactory emf;
  protected static EntityManager em;  
  
  public EntityManagerAction(EntityManagerFactory emfIn) { 
    emf = emfIn;
    em = emf.createEntityManager();
  }
  protected void doIt() { 
    if (!EntityManagerAction.em.getTransaction().isActive())
      em.getTransaction().begin();
    try {
      action();      
      em.getTransaction().commit();
    } catch (RuntimeException e) {
      if (em.isOpen() && em.getTransaction().isActive())
        em.getTransaction().rollback();
      throw e;
    } finally {
      em.close();
    }
    
  }
  public abstract void action();
}
