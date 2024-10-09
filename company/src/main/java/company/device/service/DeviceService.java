package company.device.service;

import company.device.entity.Device;
import company.device.repository.api.DeviceRepository;
import company.device.repository.api.BrandRepository;
import company.user.entity.User;
import company.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding device entity.
 */
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
     * @param deviceRepository  repository for device entity
     * @param brandRepository repository for Brand entity
     * @param userRepository repository for user entity
     */
    public DeviceService(DeviceRepository deviceRepository, BrandRepository brandRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
    }

    /**
     * Finds single device.
     *
     * @param id device's id
     * @return container with device
     */
    public Optional<Device> find(UUID id) {
        return deviceRepository.find(id);
    }

    /**
     * @param id   device's id
     * @param user existing user
     * @return selected device for user
     */
    public Optional<Device> find(User user, UUID id) {
        return deviceRepository.findByIdAndUser(id, user);
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
    public List<Device> findAll(User user) {
        return deviceRepository.findAllByUser(user);
    }

    /**
     * Creates new device.
     *
     * @param device new device
     */
    public void create(Device device) {
        deviceRepository.create(device);
    }

    /**
     * Updates existing device.
     *
     * @param device device to be updated
     */
    public void update(Device device) {
        deviceRepository.update(device);
    }

    /**
     * Deletes existing device.
     *
     * @param id existing device's id to be deleted
     */
    public void delete(UUID id) {
        deviceRepository.delete(deviceRepository.find(id).orElseThrow());
    }

    /**
     * Updates image of the device.
     *
     * @param id device's id
     * @param is input stream containing new image
     */
    public void updatePortrait(UUID id, InputStream is) {
        deviceRepository.find(id).ifPresent(device -> {
            try {
                device.setImage(is.readAllBytes());
                deviceRepository.update(device);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public Optional<List<Device>> findAllByBrand(UUID id) {
        return brandRepository.find(id)
                .map(deviceRepository::findAllByBrand);
    }

    public Optional<List<Device>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(deviceRepository::findAllByUser);
    }
}
