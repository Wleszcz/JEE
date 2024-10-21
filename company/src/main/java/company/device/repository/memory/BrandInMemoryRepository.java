package company.device.repository.memory;

import company.device.entity.Brand;
import company.device.repository.api.BrandRepository;
import company.datastore.component.DataStore;
import company.device.repository.api.DeviceRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Brand entity. Repositories should be used in business layer (e.g.: in services).
 */
@RequestScoped
public class BrandInMemoryRepository implements BrandRepository {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    @Inject
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
        store.findAllDevices().stream().filter(d -> d.getBrand().getId().equals(entity.getId())).forEach(d -> store.deleteDevice(d.getId()));
        store.deleteBrand(entity.getId());
    }

    @Override
    public void update(Brand entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }

}
