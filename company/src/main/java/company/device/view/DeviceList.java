package company.device.view;

import company.component.ModelFunctionFactory;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import company.device.model.DevicesModel;
import company.device.service.DeviceService;

import java.io.Serializable;

/**
 * View bean for rendering list of devices.
 */
@ViewScoped
@Named
public class DeviceList implements Serializable {

    /**
     * Service for managing devices.
     */
    private DeviceService service;

    /**
     * Devices list exposed to the view.
     */
    private DevicesModel devices;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public DeviceList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    /**
     * @param service service for managing characters
     */
    @EJB
    public void setService(DeviceService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all devices
     */
    public DevicesModel getDevices() {
        if (devices == null) {
            devices = factory.devicesToModel().apply(service.findAllForCallerPrincipal());
        }
        return devices;
    }

    /**
     * Action for clicking delete action.
     *
     * @param device device to be removed
     * @return navigation case to list_devices
     */
    public void deleteAction(DevicesModel.Device device) {
        service.delete(device.getId());
        devices = null;
    }

}
