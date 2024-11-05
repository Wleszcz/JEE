package company.device.view;

import company.component.ModelFunctionFactory;
import company.device.model.BrandsModel;
import company.device.model.DevicesModel;
import company.device.service.BrandService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * View bean for rendering list of brands.
 */
@ViewScoped
@Named
public class BrandList implements Serializable {

    /**
     * Service for managing brands.
     */
    private final BrandService service;

    /**
     * Brands list exposed to the view.
     */
    private BrandsModel brands;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * @param service brand service
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public BrandList(BrandService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all brands
     */
    public BrandsModel getBrands() {
        if (brands == null) {
            brands = factory.brandsToModel().apply(service.findAll());
        }
        return brands;
    }

    /**
     * Action for clicking delete action.
     *
     * @param brand brand to be removed
     * @return navigation case to list_brands
     */
    public void deleteAction(BrandsModel.Brand brand) {
        service.delete(brand.getId());
        this.brands = null;
    }

}
