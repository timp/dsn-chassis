package org.cggh.chassis.rest.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.eclipse.persistence.jpa.JpaEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.w3._2005.atom.Entry;
import org.w3._2005.atom.Feed;

public class StudyDAO {
  // http://static.springsource.org/spring/docs/3.0.5.RELEASE/reference/orm.html#orm-jpa

  private EntityManagerFactory emf;

  DataSource dataSource;
  
  @Autowired
  private String databaseName;
  @Autowired
  LocalContainerEntityManagerFactoryBean emfBean;

  @PersistenceUnit
  public void setEntityManagerFactory(EntityManagerFactory emf) {
    this.emf = emf;
  }

  public Entry getEntry(String id) {
    EntityManager em = emf.createEntityManager();
    try {
      return em.find(Entry.class, id);
    } finally {
      em.close();
    }

  }

  @SuppressWarnings("unchecked")
  public Collection<Entry> getEntries() {
    EntityManager em = emf.createEntityManager();
    try {
      Query query = em.createQuery("SELECT e FROM Entry e");
      Collection<Entry> resultsList = (Collection<Entry>) query.getResultList();
      return resultsList;
    } finally {
      em.close();
    }
  }

  public Long entryCount() {
    EntityManager em = emf.createEntityManager();
    try {
      Query query = em.createQuery("SELECT count(e) FROM Entry e");
      return (Long) query.getSingleResult();
    } finally {
      em.close();
    }
  }

  public Entry saveEntry(Entry entry) {
    if (entry.getStudyID() == null)
      entry.setStudyID(entry.getId().substring("https://www.wwarn.org/repository/service/content/".length()));
    return new EntityManagerAction(emf) {
      @Override
      public Entry action(Entry entry) {
        em.persist(entry);
        return (entry);
      }
    }.runAsTransaction(entry);
  }

  public Feed updateFeed(final Feed feed) {
    return new FeedManagerAction(emf) {
      @Override
      public Feed action(Feed feed) {
        //Due to the behaviour of merge it is necessary to return this here
        return (em.merge(feed));
      }
    }.runAsTransaction(feed);
  }
  
  public Entry updateEntry(final Entry entry) {
    return new EntityManagerAction(emf) {
      @Override
      public Entry action(Entry entry) {
        //Due to the behaviour of merge it is necessary to return this here
        return (em.merge(entry));
      }
    }.runAsTransaction(entry);
  }

  public boolean removeEntry(String id) {
    EntityManager em = emf.createEntityManager();
    Entry entry = em.find(Entry.class, id);
    boolean found = false;
    if (!em.getTransaction().isActive())
      em.getTransaction().begin();
    if (entry != null) {
      found = true;
      try {
        em.remove(entry);
        em.getTransaction().commit();
      } catch (RuntimeException e) {
        if (em.isOpen() && em.getTransaction().isActive())
          em.getTransaction().rollback();
        e.printStackTrace(); //FIXME turn log swallowing off!?
        throw e;
      }
    }
    return found;
  }

  public void evict() {
    EntityManager em = emf.createEntityManager();
    ((JpaEntityManager) em.getDelegate()).getServerSession().getIdentityMapAccessor().invalidateAll();
    try {
      Connection c = dataSource.getConnection();
      System.err.println("Db name: " + databaseName);
      System.err.println("Connection: " + c);
      Statement s = c.createStatement();
      s.execute("drop database " + databaseName);
      s.execute("create database " + databaseName);
    } catch (SQLException e) {
      e.printStackTrace(); //FIXME turn log swallowing off!?
      throw new RuntimeException(e);
    }
    em.close();
    emf.close();
    emf = null;

    // @see http://thejavablog.wordpress.com/2008/09/06/how-to-create-a-toplink-entitymanagerfactory-with-spring-outside-a-j2ee-container/
    emfBean.afterPropertiesSet();
    emf = emfBean.getObject();
    em = emf.createEntityManager();
    ((JpaEntityManager) em.getDelegate()).getServerSession().getIdentityMapAccessor().initializeAllIdentityMaps();
  }
  
  public EntityManagerFactory getEmf() {
    return emf;
  }

  public void setEmf(EntityManagerFactory emf) {
    this.emf = emf;
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
}
