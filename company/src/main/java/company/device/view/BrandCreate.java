package company.device.view;

import company.component.ModelFunctionFactory;
import company.device.model.BrandCreateModel;
import company.device.service.BrandService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.UUID;

/**
 * View bean for rendering single device create form. Creating a device is divided into number of steps where each
 * step is separate JSF view. In order to use single bean, conversation scope is used.
 */
@ViewScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class BrandCreate implements Serializable {

    /**
     * Service for managing brands.
     */
    private BrandService brandService;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;

    /**
     * Device exposed to the view.
     */
    @Getter
    private BrandCreateModel brand;

    /**
     * Injected conversation.
     */
    private final Conversation conversation;

    /**
     * @param factory      factory producing functions for conversion between models and entities
     * @param conversation injected conversation
     */
    @Inject
    public BrandCreate(
            ModelFunctionFactory factory,
            Conversation conversation) {
        this.factory = factory;
        this.conversation = conversation;
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
            brand = BrandCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String cancelAction() {
        conversation.end();
        return "/brand/brand_list.xhtml?faces-redirect=true";
    }


    /**
     * Stores new device and ends conversation.
     *
     * @return devices list navigation case
     */
    public String saveAction() {
        brandService.create(factory.modelToBrand().apply(brand));
        conversation.end();
        return "/brand/brand_list.xhtml?faces-redirect=true";
    }

    /**
     * @return current conversation id
     */
    public String getConversationId() {
        return conversation.getId();
    }

}
