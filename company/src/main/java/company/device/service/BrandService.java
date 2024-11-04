package company.device.service;

import company.device.entity.Brand;
import company.device.repository.api.BrandRepository;
import company.user.entity.UserRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding device's Brand entity.
 */
@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class BrandService {

    /**
     * Repository for Brand entity.
     */
    private final BrandRepository repository;

    /**
     * @param repository repository for Brand entity
     */
    @Inject
    public BrandService(BrandRepository repository) {
        this.repository = repository;
    }

    /**
     * @param id Brand's id
     * @return container with Brand entity
     */
    @PermitAll
    public Optional<Brand> find(UUID id) {
        return repository.find(id);
    }

    /**
     * @return all available Brands
     */
    @PermitAll
    public List<Brand> findAll() {
        return repository.findAll();
    }

    /**
     * Stores new Brand in the data store.
     *
     * @param Brand new Brand to be saved
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void create(Brand Brand) {
        repository.create(Brand);
    }


    /**
     * Deletes existing device.
     *
     * @param id existing device's id to be deleted
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void update(Brand Brand) {
        repository.update(Brand);
    }
}
