package company.device.repository.memory;

import company.device.entity.Brand;
import company.device.entity.Device;
import company.datastore.component.DataStore;
import company.device.repository.api.DeviceRepository;
import company.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Repository for device entity. Repositories should be used in business layer (e.g.: in services).
 */
public class DeviceInMemoryRepository implements DeviceRepository {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    public DeviceInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Device> find(UUID id) {
        return store.findAllDevices().stream()
                .filter(device -> device.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Device> findAll() {
        return store.findAllDevices();
    }

    @Override
    public void create(Device entity) {
        store.createDevice(entity);
    }

    @Override
    public void delete(Device entity) {
        store.deleteDevice(entity.getId());
    }

    @Override
    public void update(Device entity) {
        store.updateDevice(entity);
    }

    @Override
    public Optional<Device> findByIdAndUser(UUID id, User user) {
        return store.findAllDevices().stream()
                .filter(device -> device.getUser().equals(user))
                .filter(device -> device.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Device> findAllByUser(User user) {
        return store.findAllDevices().stream()
                .filter(device -> user.equals(device.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Device> findAllByBrand(Brand Brand) {
        return store.findAllDevices().stream()
                .filter(device -> Brand.equals(device.getBrand()))
                .collect(Collectors.toList());
    }

}
