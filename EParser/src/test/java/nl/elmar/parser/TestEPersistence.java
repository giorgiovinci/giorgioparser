package nl.elmar.parser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import nl.elmar.model.Accommodation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

//@ContextConfiguration(locations = "classpath:application-context.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional
public class TestEPersistence {

	@Test
	public void testAccommodationPersistence(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("accomodationPersistance");
	    EntityManager em = emf.createEntityManager();
	    
	    Accommodation acc = new Accommodation();
	    acc.setId("1");
	    em.persist(acc);

	}
    
}
