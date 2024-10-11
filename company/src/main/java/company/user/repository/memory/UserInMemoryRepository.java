package company.user.repository.memory;

import company.datastore.component.DataStore;
import company.user.entity.User;
import company.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for User entity. Repositories should be used in business layer (e.g.: in services).
 */
public class UserInMemoryRepository implements UserRepository {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    public UserInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<User> find(UUID id) {
        return store.findAllUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return store.findAllUsers();
    }

    @Override
    public void create(User entity) {
        store.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        store.deleteUser(entity.getId());
    }

    @Override
    public void update(User entity) {
        store.updateUser(entity);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return store.findAllUsers().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

}
