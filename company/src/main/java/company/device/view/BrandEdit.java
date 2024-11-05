package company.device.view;

import company.component.ModelFunctionFactory;
import company.device.entity.Brand;
import company.device.model.BrandEditModel;
import company.device.service.BrandService;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

/**
 * View bean for rendering single device edit form.
 */
@ViewScoped
@Named
public class BrandEdit implements Serializable {

    /**
     * Service for managing devices.
     */
    private BrandService service;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * Device id.
     */
    @Setter
    @Getter
    private UUID id;

    /**
     * Device exposed to the view.
     */
    @Getter
    private BrandEditModel brand;


    /**
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public BrandEdit(ModelFunctionFactory factory) {
        this.factory = factory;
    }
    /**
     * @param service service for managing characters
     */
    @EJB
    public void setService(BrandService service) {
        this.service = service;
    }


    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Brand> brand = service.find(id);
        if (brand.isPresent()) {
            this.brand = factory.brandToEditModel().apply(brand.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Brand not found");
        }
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() {
        service.update(factory.updateBrand().apply(service.find(id).orElseThrow(), brand));
        return "/brand/brand_list.xhtml?faces-redirect=true";
    }

}
