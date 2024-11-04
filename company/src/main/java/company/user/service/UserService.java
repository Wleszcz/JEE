package company.user.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import company.user.entity.User;
import company.user.entity.UserRoles;
import company.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding user entity.
 */
@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {

    /**
     * Repository for user entity.
     */
    private final UserRepository repository;

    /**
     * Hash mechanism used for storing users' passwords.
     */
    private final Pbkdf2PasswordHash passwordHash;

    /**
     * @param repository   repository for character entity
     * @param passwordHash hash mechanism used for storing users' passwords
     */
    @Inject
    public UserService(
            UserRepository repository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    /**
     * @return all available users
     */
    @RolesAllowed(UserRoles.ADMIN)
    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * @param id user's id
     * @return container (can be empty) with user
     */
    @RolesAllowed(UserRoles.ADMIN)
    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    /**
     * Seeks for single user using login and password. Can be used in authentication module.
     *
     * @param login user's login
     * @return container (can be empty) with user
     */
    @RolesAllowed(UserRoles.ADMIN)
    public Optional<User> find(String login) {
        return repository.findByLogin(login);
    }

    /**
     * Saves new user. Password is hashed using configured hash algorithm.
     *
     * @param user new user to be saved
     */
    @PermitAll
    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        repository.create(user);
    }

    /**
     * Deletes user.
     *
     * @param id id of user to be deleted
     */
    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    /**
     * @param login    user's login
     * @param password user's password
     * @return true if provided login and password are correct
     */
    @PermitAll
    public boolean verify(String login, String password) {
        return find(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

}
