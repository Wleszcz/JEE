package company.device.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import company.device.entity.Device;
import company.device.model.DeviceModel;
import company.device.service.DeviceService;
import company.component.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

/**
 * View bean for rendering single device information.
 */
@ViewScoped
@Named
public class DeviceView implements Serializable {

    /**
     * Service for managing devices.
     */
    private final DeviceService service;

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
    private DeviceModel device;


    /**
     * @param service service for managing devices
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public DeviceView(DeviceService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Device> device = service.find(id);
        if (device.isPresent()) {
            this.device = factory.deviceToModel().apply(device.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Device not found");
        }
    }

}
