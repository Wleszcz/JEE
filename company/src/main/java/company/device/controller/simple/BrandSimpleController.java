package company.device.controller.simple;

import company.controller.servlet.exception.NotFoundException;
import company.device.controller.api.BrandController;
import company.device.dto.GetBrandsResponse;
import company.device.service.BrandService;
import company.component.DtoFunctionFactory;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
@RequestScoped
public class BrandSimpleController implements BrandController {

    /**
     * Brand service.
     */
    private final BrandService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;


    /**
     * @param service Brand service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    @Inject
    public BrandSimpleController(BrandService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetBrandsResponse getBrands() {
        return factory.BrandsToResponse().apply(service.findAll());
    }

    @Override
    public void deleteBrand(UUID brandId) {
        service.find(brandId).ifPresentOrElse(
                entity -> service.delete(brandId),
                () -> {
                    throw new NotFoundException();
                }
        );
    }


}
