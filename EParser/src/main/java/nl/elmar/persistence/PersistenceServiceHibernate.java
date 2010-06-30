package nl.elmar.persistence;

import javax.persistence.PersistenceContextType;


import nl.elmar.model.Accommodation;
import nl.elmar.model.EParserException;

import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class PersistenceServiceHibernate extends JdoDaoSupport implements PersistService{

    @PersistenceContext(type = PersistenceContextType.TRANSACTION, unitName = "mainPersistance")
    public void setInternalEntityManager(final javax.persistence.EntityManager entityManager) {
        setEntityManager(entityManager);
    }
    
    @Override
    public Accommodation save(Accommodation accommodation) {
        if (accommodation == null) {
            throw new EParserException("accommodation is null");
        }

        try {
            
                getJpaTemplate().getEntityManager().persist(accommodation);
            
            return accommodation;
        } catch (org.springframework.dao.OptimisticLockingFailureException e) {
            throw new EParserException(e);
        } catch (org.springframework.dao.DataAccessException e) {
            throw new EParserException(e);
        }
    }

}
