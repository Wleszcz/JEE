package company.repository.persistence;

import company.device.entity.Brand;
import company.device.repository.api.BrandRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Brand entity. Repositories should be used in business layer (e.g.: in services). The request
 * scope is a result of the fact that {@link EntityManager} objects cannot be used in multiple threads (are not thread
 * safe). Because services are CDI application scoped beans (technically singletons) then repositories must be thread
 * scoped in order to ensure single entity manager for single thread.
 */
@RequestScoped
public class BrandPersistenceRepository implements BrandRepository {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Brand> find(UUID id) {
        return Optional.ofNullable(em.find(Brand.class, id));
    }

    @Override
    public List<Brand> findAll() {
        return em.createQuery("select p from Brand p", Brand.class).getResultList();
    }

    @Override
    public void create(Brand entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Brand entity) {
        /* Clearing cache used as workaround when not handling both sides of relationships, not recommended. */
//        em.getEntityManagerFactory().getCache().evictAll(); //Clearing 2nd level cache.
//        em.clear(); //Clearing 1st level cache.
        em.remove(em.find(Brand.class, entity.getId()));
    }

    @Override
    public void update(Brand entity) {
        em.merge(entity);
    }

}
