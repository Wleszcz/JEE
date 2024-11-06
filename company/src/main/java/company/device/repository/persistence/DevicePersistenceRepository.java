package company.device.repository.persistence;

import company.device.entity.Brand;
import company.device.entity.Device;
import company.device.entity.Device_;
import company.device.repository.api.DeviceRepository;
import company.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Device entity. Repositories should be used in business layer (e.g.: in services). The request scope
 * is a result of the fact that {@link EntityManager} objects cannot be used in multiple threads (are not thread safe).
 * Because services are CDI application scoped beans (technically singletons) then repositories must be thread scoped in
 * order to ensure single entity manager for single thread.
 */
@ApplicationScoped
public class DevicePersistenceRepository implements DeviceRepository {

    /**
     * Connection with the database (not thread safe).
     */
    private final EntityManager em;

    @Inject
    public DevicePersistenceRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Device> find(UUID id) {
        return Optional.ofNullable(em.find(Device.class, id));
    }

    @Override
    public List<Device> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> query = cb.createQuery(Device.class);
        Root<Device> root = query.from(Device.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Device entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.persist(entity);
    }

    @Override
    public void delete(Device entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.remove(em.find(Device.class, entity.getId()));
    }

    @Override
    public void update(Device entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.merge(entity);
    }

    @Override
    public void detach(Device entity) {
        em.detach(entity);
    }


    @Override
    public Optional<Device> findByIdAndUser(UUID id, User user) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Device> query = cb.createQuery(Device.class);
            Root<Device> root = query.from(Device.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Device_.user), user),
                            cb.equal(root.get(Device_.id), id)
                    ));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Device> findAllByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> query = cb.createQuery(Device.class);
        Root<Device> root = query.from(Device.class);
        query.select(root)
                .where(cb.equal(root.get(Device_.user), user));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Device> findAllByBrand(Brand brand) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> query = cb.createQuery(Device.class);
        Root<Device> root = query.from(Device.class);
        query.select(root)
                .where(cb.equal(root.get(Device_.brand), brand));
        return em.createQuery(query).getResultList();
    }

}
