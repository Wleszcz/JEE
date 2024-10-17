package company.user.service;

import company.crypto.component.Pbkdf2PasswordHash;
import company.device.entity.Device;
import company.user.entity.User;
import company.user.repository.api.FileRepository;
import company.user.repository.api.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding user entity.
 */
@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserService {

    /**
     * Repository for user entity.
     */
    private final UserRepository repository;

    /**
     * Repository for user entity.
     */
    private final FileRepository fileRepository;

    /**
     * Hash mechanism used for storing users' passwords.
     */
    private final Pbkdf2PasswordHash passwordHash;

    /**
     * @param repository   repository for device entity
     * @param passwordHash hash mechanism used for storing users' passwords
     */
    @Inject
    public UserService(UserRepository repository, FileRepository fileRepository, Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.fileRepository = fileRepository;
        this.passwordHash = passwordHash;
    }

    /**
     * @param id user's id
     * @return container (can be empty) with user
     */
    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    /**
     * @return container (can be empty) with users
     */
    public List<User> findAll() {
        return repository.findAll();
    }


    /**
     * Seeks for single user using login and password. Can be used in authentication module.
     *
     * @param login user's login
     * @return container (can be empty) with user
     */
    public Optional<User> find(String login) {
        return repository.findByLogin(login);
    }

    /**
     * Saves new user. Password is hashed using configured hash algorithm.
     *
     * @param user new user to be saved
     */
    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        repository.create(user);
    }

    /**
     * @param login    user's login
     * @param password user's password
     * @return true if provided login and password are correct
     */
    public boolean verify(String login, String password) {
        return find(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }
    /**
     * Updates existing device.
     *
     * @param user device to be updated
     */
    public void update(User user) {
        repository.update(user);
    }

    /**
     * Deletes existing user.
     *
     * @param id existing device's id to be deleted
     */
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
        fileRepository.delete(id);
    }

    /**
     * Updates image of the device.
     *
     * @param id device's id
     * @param is input stream containing new image
     */
    public void updateImage(UUID id, InputStream is) {
        repository.find(id).ifPresent(user -> {
            try {
                fileRepository.save(id, is);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    /**
     * Updates image of the user.
     *
     * @param id user's id
     */
    public Optional<byte[]> getImage(UUID id) {
        return fileRepository.read(id);
    }

    /**
     * Deletes image of the user.
     *
     * @param id device's id
     */
    public void deleteUserImage(UUID id) {
        fileRepository.delete(id);
    }
}
