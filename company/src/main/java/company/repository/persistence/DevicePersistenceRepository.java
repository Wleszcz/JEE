package company.repository.persistence;

import company.device.entity.Brand;
import company.device.entity.Device;
import company.device.repository.api.DeviceRepository;
import company.user.entity.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Device entity. Repositories should be used in business layer (e.g.: in services). The request scope
 * is a result of the fact that {@link EntityManager} objects cannot be used in multiple threads (are not thread safe).
 * Because services are CDI application scoped beans (technically singletons) then repositories must be thread scoped in
 * order to ensure single entity manager for single thread.
 */
@RequestScoped
public class DevicePersistenceRepository implements DeviceRepository {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Device> find(UUID id) {
        return Optional.ofNullable(em.find(Device.class, id));
    }

    @Override
    public List<Device> findAll() {
        return em.createQuery("select c from Device c", Device.class).getResultList();
    }

    @Override
    public void create(Device entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Device entity) {
        em.remove(em.find(Device.class, entity.getId()));
    }

    @Override
    public void update(Device entity) {
        em.merge(entity);
    }

    @Override
    public Optional<Device> findByIdAndUser(UUID id, User user) {
        try {
            return Optional.of(em.createQuery("select c from Device c where c.id = :id and c.user = :user", Device.class)
                    .setParameter("user", user)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Device> findAllByUser(User user) {
        return em.createQuery("select c from Device c where c.user = :user", Device.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Device> findAllByBrand(Brand brand) {
        return em.createQuery("select c from Device c where c.brand = :brand", Device.class)
                .setParameter("brand", brand)
                .getResultList();
    }

}
