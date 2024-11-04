package company.device.service;

import company.device.entity.Device;
import company.device.repository.api.BrandRepository;
import company.device.repository.api.DeviceRepository;
import company.user.entity.User;
import company.user.entity.UserRoles;
import company.user.repository.api.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding device entity.
 */
@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class DeviceService {

    /**
     * Repository for device entity.
     */
    private final DeviceRepository deviceRepository;

    /**
     * Repository for Brand entity.
     */
    private final BrandRepository brandRepository;

    /**
     * Repository for user entity.
     */
    private final UserRepository userRepository;

    /**
     * Security context
     */
    private final SecurityContext securityContext;

    /**
     * @param deviceRepository repository for device entity
     * @param brandRepository  repository for Brand entity
     * @param userRepository   repository for user entity
     */
    @Inject
    public DeviceService(DeviceRepository deviceRepository, BrandRepository brandRepository, UserRepository userRepository, @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.deviceRepository = deviceRepository;
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }

    /**
     * Finds single device.
     *
     * @param id device's id
     * @return container with device
     */
    @RolesAllowed(UserRoles.USER)
    public Optional<Device> find(UUID id) {
        return deviceRepository.find(id);
    }

    /**
     * @param id   device's id
     * @param user existing user
     * @return selected device for user
     */
    @RolesAllowed(UserRoles.USER)
    public Optional<Device> find(User user, UUID id) {
        return deviceRepository.findByIdAndUser(id, user);
    }


    /**
     * @return selected character owned by the authenticated user
     */
    @RolesAllowed(UserRoles.USER)
    public Optional<Device> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return find(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(user, id);
    }


    /**
     * @return all available devices
     */
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    /**
     * @param user existing user, device's owner
     * @return all available devices of the selected user
     */
    @RolesAllowed(UserRoles.USER)
    public List<Device> findAll(User user) {
        return deviceRepository.findAllByUser(user);
    }

    /**
     * @return all available characters to th authenticated user
     */
    @RolesAllowed(UserRoles.USER)
    public List<Device> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return findAll();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAll(user);
    }


    /**
     * Creates new device.
     *
     * @param device new device
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void create(Device device) {
        if (deviceRepository.find(device.getId()).isPresent()) {
            throw new IllegalArgumentException("Character already exists.");
        }
        if (brandRepository.find(device.getBrand().getId()).isEmpty()) {
            throw new IllegalArgumentException("Profession does not exists.");
        }
        deviceRepository.create(device);
    }


    /**
     * Creates new character for current caller principal.
     *
     * @param device new
     */
    @RolesAllowed(UserRoles.USER)
    public void createForCallerPrincipal(Device device) {
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        device.setUser(user);
        create(device);
    }


    /**
     * Updates existing device.
     *
     * @param device device to be updated
     */
    @RolesAllowed(UserRoles.USER)
    public void update(Device device) {
        checkAdminRoleOrOwner(deviceRepository.find(device.getId()));
        deviceRepository.update(device);
    }

    /**
     * Deletes existing device.
     *
     * @param id existing device's id to be deleted
     */

    public void delete(UUID id) {
        checkAdminRoleOrOwner(deviceRepository.find(id));
        deviceRepository.delete(deviceRepository.find(id).orElseThrow());
    }

    /**
     * Updates image of the device.
     *
     * @param id device's id
     * @param is input stream containing new image
     */
//    @Transactional
//    public void updatePortrait(UUID id, InputStream is) {
//        deviceRepository.find(id).ifPresent(device -> {
//            try {
//                device.setImage(is.readAllBytes());
//                deviceRepository.update(device);
//            } catch (IOException ex) {
//                throw new IllegalStateException(ex);
//            }
//        });
//    }
    @RolesAllowed(UserRoles.USER)
    public Optional<List<Device>> findAllByBrand(UUID id) {
        return brandRepository.find(id)
                .map(deviceRepository::findAllByBrand);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<List<Device>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(deviceRepository::findAllByUser);
    }


    /**
     * @param device to be checked
     * @throws EJBAccessException when caller principal has no admin role and is not character's owner
     */
    private void checkAdminRoleOrOwner(Optional<Device> device) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRoles.USER)
                && device.isPresent()
                && device.get().getUser().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }

}
