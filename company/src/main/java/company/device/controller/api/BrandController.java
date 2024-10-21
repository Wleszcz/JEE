package company.device.controller.api;

import company.device.dto.GetBrandsResponse;

import java.util.UUID;

/**
 * Controller for managing collections Brands' representations.
 */
public interface BrandController {

    /**
     * @return all Brands representation
     */
    GetBrandsResponse getBrands();

    void deleteBrand(UUID brandId);
}
