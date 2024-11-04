package company.device.view;

import company.component.ModelFunctionFactory;
import company.device.entity.DeviceType;
import company.device.model.UserCreateModel;
import company.user.service.UserService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * View bean for rendering single device create form. Creating a device is divided into number of steps where each
 * step is separate JSF view. In order to use single bean, conversation scope is used.
 */
@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class UserCreate implements Serializable {


    private final ModelFunctionFactory factory;
    private UserService userService;

    /**
     * Device exposed to the view.
     */
    @Getter
    private UserCreateModel user;

    /**
     * Injected conversation.
     */
    private final Conversation conversation;

    /**
     * @param factory           factory producing functions for conversion between models and entities
     * @param conversation      injected conversation
     */
    @Inject
    public UserCreate(
            ModelFunctionFactory factory,
            Conversation conversation) {
        this.factory = factory;
        this.conversation = conversation;
    }


    /**
     * @param userService service for managing characters
     */
    @EJB
    public void setUserService( UserService userService) {
        this.userService = userService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view. @PostConstruct method is called after h:form header is already
     * rendered. Conversation should be started in f:metadata/f:event.
     */
    public void init() {
        if (conversation.isTransient()) {
            user = UserCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String cancelAction() {
        conversation.end();
        return "/index.xhtml?faces-redirect=true";
    }



    public String saveAction() {
        userService.create(factory.modelToUser().apply(user));
        conversation.end();
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     * @return current conversation id
     */
    public String getConversationId() {
        return conversation.getId();
    }

}
