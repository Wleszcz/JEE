package company.user.entity;

import company.device.entity.Device;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

    private String email;

    /**
     * List of user's devices.
     */
    @ToString.Exclude//It's common to exclude lists from toString
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Device> devices;



    /**
     * User's security roles.
     */
    @CollectionTable(name = "users__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    /**
     * Devices Example image. Images in database are stored as blobs (binary large objects).
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] image;
}
