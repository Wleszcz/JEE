package company.repository.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.extern.java.Log;

/**
 * Producers {@link EntityManager} in {@link RequestScoped}.
 */
@Log
@ApplicationScoped
public class EntityManagerProducer {

    /**
     * Global thread safe factory.
     */
    private EntityManagerFactory factory;

    /**
     * @param factory global thread safe factory
     */
    @PersistenceUnit
    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    /**
     * Produces CDI managed {@link EntityManager}. Caution, it is application managed instance.
     *
     * @return application managed {@link EntityManager}
     */
    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return factory.createEntityManager();
    }

    /**
     * Closes disposed entity manager.
     *
     * @param em entity manager
     */
    public void disposeEntityManager(@Disposes EntityManager em) {
        log.info("Disposing %s".formatted(em));
        em.close();
    }

}
