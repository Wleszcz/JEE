package company.datastore.component;

import company.device.entity.Device;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import company.device.entity.Brand;
import company.serialization.component.CloningUtility;
import company.user.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * For the sake of simplification instead of using real database this example is using a data source object which should
 * be put in servlet context in a single instance. In order to avoid {@link java.util.ConcurrentModificationException}
 * all methods are synchronized. Normally synchronization would be carried on by the database server. Caution, this is
 * very inefficient implementation but can be used to present other mechanisms without obscuration example with ORM
 * usage.
 */
@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    /**
     * Set of all available Brands.
     */
    private final Set<Brand> Brands = new HashSet<>();

    /**
     * Set of all devices.
     */
    private final Set<Device> devices = new HashSet<>();

    /**
     * Set of all users.
     */
    private final Set<User> users = new HashSet<>();

    /**
     * Component used for creating deep copies.
     */
    private final CloningUtility cloningUtility;

    /**
     * @param cloningUtility component used for creating deep copies
     */
    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    /**
     * Seeks for all Brands.
     *
     * @return list (can be empty) of all Brands
     */
    public synchronized List<Brand> findAllBrands() {
        return Brands.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new Brand.
     *
     * @param value new Brand to be stored
     * @throws IllegalArgumentException if Brand with provided id already exists
     */
    public synchronized void createBrand(Brand value) throws IllegalArgumentException {
        if (Brands.stream().anyMatch(Brand -> Brand.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The Brand id \"%s\" is not unique".formatted(value.getId()));
        }
        Brands.add(cloningUtility.clone(value));
    }

    /**
     * Seeks for all devices.
     *
     * @return list (can be empty) of all devices
     */
    public synchronized List<Device> findAllDevices() {
        return devices.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new device.
     *
     * @param value new device to be stored
     * @throws IllegalArgumentException if device with provided id already exists or when {@link User} or
     *                                  {@link Brand} with provided uuid does not exist
     */
    public synchronized void createDevice(Device value) throws IllegalArgumentException {
        if (devices.stream().anyMatch(device -> device.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The devices id \"%s\" is not unique".formatted(value.getId()));
        }
        Device entity = cloneWithRelationships(value);
        devices.add(entity);
    }

    /**
     * Updates existing device.
     *
     * @param value device to be updated
     * @throws IllegalArgumentException if device with the same id does not exist or when {@link User} or
     *                                  {@link Brand} with provided uuid does not exist
     */
    public synchronized void updateDevice(Device value) throws IllegalArgumentException {
        Device entity = cloneWithRelationships(value);
        if (devices.removeIf(device -> device.getId().equals(value.getId()))) {
            devices.add(entity);
        } else {
            throw new IllegalArgumentException("The device with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /**
     * Deletes existing device.
     *
     * @param id id of device to be deleted
     * @throws IllegalArgumentException if device with provided id does not exist
     */
    public synchronized void deleteDevice(UUID id) throws IllegalArgumentException {
        if (!devices.removeIf(device -> device.getId().equals(id))) {
            throw new IllegalArgumentException("The device with id \"%s\" does not exist".formatted(id));
        }
    }

    /**
     * Seeks for all users.
     *
     * @return list (can be empty) of all users
     */
    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new user.
     *
     * @param value new user to be stored
     * @throws IllegalArgumentException if user with provided id already exists
     */
    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    /**
     * Updates existing user.
     *
     * @param value user to be updated
     * @throws IllegalArgumentException if user with the same id does not exist
     */
    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(user -> user.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /**
     * Clones existing device and updates relationships for values in storage
     *
     * @param value device
     * @return cloned value with updated relationships
     * @throws IllegalArgumentException when {@link User} or {@link Brand} with provided uuid does not exist
     */
    private Device cloneWithRelationships(Device value) {
        Device entity = cloningUtility.clone(value);

        if (entity.getUser() != null) {
            entity.setUser(users.stream()
                    .filter(user -> user.getId().equals(value.getUser().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getId()))));
        }

        if (entity.getBrand() != null) {
            entity.setBrand(Brands.stream()
                    .filter(Brand -> Brand.getId().equals(value.getBrand().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No Brand with id \"%s\".".formatted(value.getBrand().getId()))));
        }

        return entity;
    }

    /**
     * Deletes existing user.
     *
     * @param id id of user to be deleted
     * @throws IllegalArgumentException if user with provided id does not exist
     */
    public synchronized void deleteUser(UUID id) throws IllegalArgumentException {
        if (!users.removeIf(user -> user.getId().equals(id))) {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(id));
        }
    }

}
