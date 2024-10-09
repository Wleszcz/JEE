package company.device.repository.api;

import company.device.entity.Device;
import company.device.entity.Brand;
import company.repository.api.Repository;
import company.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for device entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface DeviceRepository extends Repository<Device, UUID> {

    /**
     * Seeks for single user's device.
     *
     * @param id   device's id
     * @param user device's owner
     * @return container (can be empty) with device
     */
    Optional<Device> findByIdAndUser(UUID id, User user);

    /**
     * Seeks for all user's devices.
     *
     * @param user devices' owner
     * @return list (can be empty) of user's devices
     */
    List<Device> findAllByUser(User user);

    /**
     * Seeks for all Brand's devices.
     *
     * @param Brand device's Brand
     * @return list (can be empty) of user's devices
     */
    List<Device> findAllByBrand(Brand Brand);

}
