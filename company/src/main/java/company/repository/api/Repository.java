package company.repository.api;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing data from underlying data stores. The interface does not define how the data
 * is retrieved. The interface defines only basic methods which are not aware of entity object structure. Additional
 * methods (like find by or order) should be defined as methods in implementing class.
 *
 * @param <E> type of the entity
 * @param <K> type of the primary key
 */
public interface Repository<E, K> {

    /**
     * Find entity object using its primary key.
     *
     * @param id object primary key
     * @return container (can be empty) with entity object
     */
    Optional<E> find(K id);

    /**
     * Find all entities.
     *
     * @return list (can be empty) with all objects
     */
    List<E> findAll();

    /**
     * Save new object in the data store.
     *
     * @param entity object to be saved
     */
    void create(E entity);

    /**
     * Delete object from the data store.
     *
     * @param entity object to be deleted
     */
    void delete(E entity);

    /**
     * Update existing object in the data store.
     *
     * @param entity object to be updated
     */
    void update(E entity);

}
