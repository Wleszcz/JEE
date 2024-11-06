package company.device.view;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import company.device.entity.Device;
import company.device.model.DeviceEditModel;
import company.device.service.DeviceService;
import company.component.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

/**
 * View bean for rendering single device edit form.
 */
@ViewScoped
@Named
public class DeviceEdit implements Serializable {

    /**
     * Service for managing devices.
     */
    private DeviceService service;

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
    private DeviceEditModel device;

    /**
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public DeviceEdit(ModelFunctionFactory factory) {
        this.factory = factory;
    }
    /**
     * @param service service for managing characters
     */
    @EJB
    public void setService(DeviceService service) {
        this.service = service;
    }

    Device deviceEntity;
    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Device> device = service.findForCallerPrincipal(id);
        if (device.isPresent()) {
            this.device = factory.deviceToEditModel().apply(device.get());
            deviceEntity =service.find(id).orElseThrow();
            System.out.println(this.device.getVersion());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Device not found");
        }
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() throws IOException {
        try {
            service.update(factory.updateDevice().apply(deviceEntity, device));
            return "/device/device_list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            String versionError = "Wystąpił konflikt wersji. Dane zostały zmodyfikowane przez innego użytkownika. Czy chcesz kontynuować ?";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, versionError, versionError);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            deviceEntity.setVersion(deviceEntity.getVersion()+1);
            return null;
        }
    }

    public String reset() throws IOException {
        init();
        return null;
    }

}
