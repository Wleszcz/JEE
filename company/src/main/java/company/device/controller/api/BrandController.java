package company.device.controller.api;

import company.device.dto.GetBrandsResponse;

/**
 * Controller for managing collections Brands' representations.
 */
public interface BrandController {

    /**
     * @return all Brands representation
     */
    GetBrandsResponse getBrands();

}
