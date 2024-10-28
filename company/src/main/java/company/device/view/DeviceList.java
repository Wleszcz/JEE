package company.device.view;

import company.component.ModelFunctionFactory;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import company.device.model.DevicesModel;
import company.device.service.DeviceService;

/**
 * View bean for rendering list of devices.
 */
@RequestScoped
@Named
public class DeviceList {

    /**
     * Service for managing devices.
     */
    private final DeviceService service;

    /**
     * Devices list exposed to the view.
     */
    private DevicesModel devices;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * @param service device service
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public DeviceList(DeviceService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all devices
     */
    public DevicesModel getDevices() {
        if (devices == null) {
            devices = factory.devicesToModel().apply(service.findAll());
        }
        return devices;
    }

    /**
     * Action for clicking delete action.
     *
     * @param device device to be removed
     * @return navigation case to list_devices
     */
    public String deleteAction(DevicesModel.Device device) {
        service.delete(device.getId());
        return "device_list?faces-redirect=true";
    }

}
