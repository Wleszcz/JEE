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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);


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
     * @param brand new Brand to be saved
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void create(Brand brand) {
        repository.create(brand);
        logger.info("Brand with ID: {} has been created.", brand.getId());
    }


    /**
     * Deletes existing device.
     *
     * @param id existing device's id to be deleted
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
        logger.info("Brand with ID: {} has been deleted.", id);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void update(Brand brand) {
        repository.update(brand);
        logger.info("Brand with ID: {} has been edited.", brand.getId());
    }
}
