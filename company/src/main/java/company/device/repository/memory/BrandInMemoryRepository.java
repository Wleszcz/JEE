package company.device.repository.memory;

import company.device.entity.Brand;
import company.device.repository.api.BrandRepository;
import company.datastore.component.DataStore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Brand entity. Repositories should be used in business layer (e.g.: in services).
 */
public class BrandInMemoryRepository implements BrandRepository {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    public BrandInMemoryRepository(DataStore store) {
        this.store = store;
    }


    @Override
    public Optional<Brand> find(UUID id) {
        return store.findAllBrands().stream()
                .filter(Brand -> Brand.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Brand> findAll() {
        return store.findAllBrands();
    }

    @Override
    public void create(Brand entity) {
        store.createBrand(entity);
    }

    @Override
    public void delete(Brand entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }

    @Override
    public void update(Brand entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }

}
