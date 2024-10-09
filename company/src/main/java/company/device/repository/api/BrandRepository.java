package company.device.repository.api;

import company.device.entity.Brand;
import company.repository.api.Repository;

import java.util.UUID;

/**
 * Repository for Brand entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface BrandRepository extends Repository<Brand, UUID> {

}
