package company.device.service;

import company.device.entity.Brand;
import company.device.repository.api.BrandRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding device's Brand entity.
 */
public class BrandService {

    /**
     * Repository for Brand entity.
     */
    private final BrandRepository repository;

    /**
     * @param repository repository for Brand entity
     */
    public BrandService(BrandRepository repository) {
        this.repository = repository;
    }

    /**
     * @param id Brand's id
     * @return container with Brand entity
     */
    public Optional<Brand> find(UUID id) {
        return repository.find(id);
    }

    /**
     * @return all available Brands
     */
    public List<Brand> findAll() {
        return repository.findAll();
    }

    /**
     * Stores new Brand in the data store.
     *
     * @param Brand new Brand to be saved
     */
    public void create(Brand Brand) {
        repository.create(Brand);
    }

}
