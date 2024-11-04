package company.user.entity;

import company.device.entity.Device;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Entity for system user. Represents information about particular user as well as credentials for authorization and
 * authentication needs.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User implements Serializable {

    /**
     * Unique id (primary key).
     */
    @Id
    private UUID id;

    /**
     * User's login.
     */
    private String login;

    /**
     * User's given name.
     */
    private String name;

    /**
     * User's surname.
     */
    private String surname;

    /**
     * User's birthdate.
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * User's password.
     */
    @ToString.Exclude
    private String password;

    /**
     * User's contact email.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * List of user's characters.
     */
    @ToString.Exclude//It's common to exclude lists from toString
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Device> devices;

    /**
     * User's security roles.
     */
    @CollectionTable(name = "users__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

}
