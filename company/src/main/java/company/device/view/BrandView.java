package company.device.view;

import company.component.ModelFunctionFactory;
import company.device.entity.Brand;
import company.device.entity.Device;
import company.device.model.BrandModel;
import company.device.model.DevicesModel;
import company.device.service.BrandService;
import company.device.service.DeviceService;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * View bean for rendering single brand information.
 */
@ViewScoped
@Named
public class BrandView implements Serializable {

    /**
     * Service for managing brands.
     */
    private final BrandService brandService;

    private final DeviceService deviceService;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * brand id.
     */
    @Setter
    @Getter
    private UUID id;

    /**
     * brand exposed to the view.
     */
    @Getter
    private BrandModel brand;

    @Getter
    @Setter
    private DevicesModel devicesModel;


    /**
     * @param brandService service for managing brands
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public BrandView(BrandService brandService, DeviceService deviceService, ModelFunctionFactory factory) {
        this.brandService = brandService;
        this.deviceService = deviceService;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Brand> brand = brandService.find(id);
        Optional<List<Device>> devices = deviceService.findAllByBrand(id);
        if (brand.isPresent()) {
            this.brand = factory.brandToModel().apply(brand.get());
            this.id = brand.get().getId();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Brand not found");
        }

        if(devices.isPresent()) {
            setDevicesModel(factory.devicesToModel().apply(devices.get()));
        }
        else {
            setDevicesModel(new DevicesModel());
        }
    }

}
