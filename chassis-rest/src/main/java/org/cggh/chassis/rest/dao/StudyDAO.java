package org.cggh.chassis.rest.dao;

import java.lang.IllegalArgumentException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.w3._2005.atom.Entry;
import org.apache.commons.dbcp.BasicDataSource;
import org.cggh.chassis.rest.bean.Options;
import org.eclipse.persistence.jpa.JpaEntityManager;

public class StudyDAO {
	// http://static.springsource.org/spring/docs/3.0.5.RELEASE/reference/orm.html#orm-jpa

	private EntityManagerFactory emf;
	private EntityManager em;

  @Autowired
  BasicDataSource dataSource;
  @Autowired
  Options options;
  private String databaseName;
  @Autowired
  LocalContainerEntityManagerFactoryBean emfBean;
	

	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
    this.em = this.emf.createEntityManager();
	}

  public Entry getEntry(String id) {
    final Entry beta = em.find(Entry.class, id);
    return beta;
  }

  @SuppressWarnings("unchecked")
  public Collection<Entry> getAll() {
    Query query = em.createQuery("SELECT e FROM Entry e");
    Collection<Entry> resultsList = (Collection<Entry>)query.getResultList();
    return resultsList;
  }

	public void saveEntry(Entry entry) {
    if (!em.getTransaction().isActive())
      em.getTransaction().begin();
		try {
      em.persist(entry);
      em.getTransaction().commit();
    } catch (RuntimeException e) { 
      if (em.isOpen() &&  em.getTransaction().isActive())
        em.getTransaction().rollback();
      throw e;
		}
	}

	public void updateEntry(String id, Entry entry) {
    if (!em.getTransaction().isActive())
      em.getTransaction().begin();
    try {	
		  em.merge(entry);
	    em.getTransaction().commit();
    } catch (RuntimeException e) { 
      if (em.isOpen() &&  em.getTransaction().isActive())
        em.getTransaction().rollback();
      throw e;
    }
	}

	public boolean remove(String id)  {
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
        if (em.isOpen() &&  em.getTransaction().isActive())
          em.getTransaction().rollback();
        throw e;
      }
		}
		return found;
	}

  public void evict() {
    ((JpaEntityManager)em.getDelegate()).getServerSession().getIdentityMapAccessor().invalidateAll();
    try {
      if (options.get("databaseName") == null)
        throw new IllegalArgumentException("Option databaseName not set");
      databaseName = options.get("databaseName");
      Connection c = dataSource.getConnection();
      System.err.println("Db name: " + databaseName);
      System.err.println("Connection: " + c);
      Statement s = c.createStatement();
      s.execute("drop database " + databaseName);
      s.execute("create database " + databaseName);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }    
    em.close();
    emf.close();
    emf = null;
    
    // @see http://thejavablog.wordpress.com/2008/09/06/how-to-create-a-toplink-entitymanagerfactory-with-spring-outside-a-j2ee-container/
    emfBean.afterPropertiesSet();
    emf =  emfBean.getObject();
    em = emf.createEntityManager();
    ((JpaEntityManager)em.getDelegate()).getServerSession().getIdentityMapAccessor().initializeAllIdentityMaps();
  }
}
