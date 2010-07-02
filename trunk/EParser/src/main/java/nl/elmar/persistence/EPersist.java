package nl.elmar.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import nl.elmar.model.Accommodation;

public class EPersist {

    
    private EntityManager em = null;
    
    public EPersist(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("accomodationPersistance");
        em = emf.createEntityManager();    
    }
    
    public void save(Accommodation accommodation){
        em.getTransaction().begin();
        em.persist(accommodation);
        em.getTransaction().commit();
 
    }
    
    public List<Accommodation> getAll(){
        Query query = em.createQuery("select a from Accommodation a");
        return query.getResultList();
    }
    
}
