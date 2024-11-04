package company.device.model;

import company.device.entity.DeviceType;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents new device to be created. Includes oll
 * fields which can be used in device creation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UserCreateModel {

    /**
     * Device's id.
     */
    private UUID id;

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

}
