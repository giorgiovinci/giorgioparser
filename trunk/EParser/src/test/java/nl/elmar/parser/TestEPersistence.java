package nl.elmar.parser;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import nl.elmar.model.Accommodation;
import nl.elmar.model.Price;
import nl.elmar.model.Unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

//@ContextConfiguration(locations = "classpath:application-context.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class TestEPersistence {

	@Test
	public void testAccommodationPersistence(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("accomodationPersistance");
	    EntityManager em = emf.createEntityManager();
	    
	    Price price = new Price();
	    price.setAmount("100");
	    List<Price> listPrice = new ArrayList<Price>();
	    
	    
	    Unit unit = new Unit();
	    unit.setId("1");
	    unit.setDescription("desc");
	    List<Unit> listUnit = new ArrayList<Unit>();
	    listUnit.add(unit);
	    
	    unit.setPrices(listPrice);
	    
	    em.getTransaction().begin();

	    Accommodation acc = new Accommodation();
	    acc.setId("1");
	    acc.setName("test");
	    acc.setUnits(listUnit);
	    
	    em.persist(acc);
	    em.getTransaction().commit();

	    Query query = em.createQuery("select a from Accommodation a");
	    List<Accommodation> accList = query.getResultList();
	    for(Accommodation a: accList){
	    	System.out.println(a.getId());
	    }
	    
	}
    
	@Test
	public void testAccommodationRead(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("accomodationPersistance");
	    EntityManager em = emf.createEntityManager();
	    
	    Query query = em.createQuery("select a from Accommodation a");
	    List<Accommodation> accList = query.getResultList();
	    System.out.println(accList);
	    
	    Query nquery = em.createNativeQuery("select id from Accommodation");
	    List<String> ids = nquery.getResultList();
	    System.out.println(ids);
	}
}
