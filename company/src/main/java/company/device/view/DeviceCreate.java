package company.device.view;

import company.device.entity.DeviceType;
import company.device.model.UserModel;
import company.user.service.UserService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import company.device.model.DeviceCreateModel;
import company.device.model.BrandModel;
import company.device.service.DeviceService;
import company.device.service.BrandService;
import company.component.ModelFunctionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * View bean for rendering single device create form. Creating a device is divided into number of steps where each
 * step is separate JSF view. In order to use single bean, conversation scope is used.
 */
@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class DeviceCreate implements Serializable {

    /**
     * Service for managing devices.
     */
    private DeviceService deviceService;

    /**
     * Service for managing brands.
     */
    private BrandService brandService;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;
    private final UserService userService;

    /**
     * Device exposed to the view.
     */
    @Getter
    private DeviceCreateModel device;

    /**
     * Available brands.
     */
    @Getter
    private List<BrandModel> brands;

    @Getter
    private List<UserModel> users;

    /**
     * Injected conversation.
     */
    private final Conversation conversation;

    /**
     * @param deviceService  service for managing devices
     * @param brandService service for managing brands
     * @param factory           factory producing functions for conversion between models and entities
     * @param conversation      injected conversation
     */
    @Inject
    public DeviceCreate(
            ModelFunctionFactory factory,
            Conversation conversation,
            UserService userService) {
        this.factory = factory;
        this.conversation = conversation;
        this.userService = userService;
    }

    /**
     * @param deviceService service for managing characters
     */
    @EJB
    public void setDeviceService( DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * @param brandService service for managing professions
     */
    @EJB
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }


    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view. @PostConstruct method is called after h:form header is already
     * rendered. Conversation should be started in f:metadata/f:event.
     */
    public void init() {
        if (conversation.isTransient()) {
            brands = brandService.findAll().stream()
                    .map(factory.brandToModel())
                    .collect(Collectors.toList());
            System.out.println(brands);

            users = userService.findAll().stream()
                    .map(factory.userToModel())
                    .collect(Collectors.toList());
            device = DeviceCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    /**
     * @return brand navigation case
     */
    public String goToBrandAction() {
        return "/device/device_create__brand.xhtml?faces-redirect=true";
    }



    /**
     * @return portrait navigation case
     */
    public String goToPortraitAction() {
        return "/device/device_create__portrait.xhtml?faces-redirect=true";
    }



    /**
     * @return basic information navigation case
     */
    public Object goToBasicAction() {
        return "/device/device_create__basic.xhtml?faces-redirect=true";
    }

    /**
     * Cancels device creation process.
     *
     * @return devices list navigation case
     */
    public String cancelAction() {
        conversation.end();
        return "/device/device_list.xhtml?faces-redirect=true";
    }

    /**
     * Sets default device properties (leve land health).
     *
     * @return confirmation navigation case
     */
    public String goToConfirmAction() {
        device.setMass(0);
        device.setPrice(0);
        return "/device/device_create__confirm.xhtml?faces-redirect=true";
    }

    /**
     * Stores new device and ends conversation.
     *
     * @return devices list navigation case
     */
    public String saveAction() {
        deviceService.createForCallerPrincipal(factory.modelToDevice().apply(device));
        conversation.end();
        return "/device/device_list.xhtml?faces-redirect=true";
    }

    /**
     * @return current conversation id
     */
    public String getConversationId() {
        return conversation.getId();
    }

    public String getDevicePortraitUrl() {
        return "/view/api/v1/devices/new/portrait?cid=%s".formatted(getConversationId());
    }

    public List<DeviceType> getDeviceTypeList() {
        return List.of(DeviceType.TV, DeviceType.FRIDGE, DeviceType.PHONE, DeviceType.TABLET);
    }
}
