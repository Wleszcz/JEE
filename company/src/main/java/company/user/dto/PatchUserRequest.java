package company.user.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * PATCH user request. Contains only fields which can be changed byt the user while updating its profile. User is
 * defined in {@link company.user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchUserRequest {

    /**
     * User's name.
     */
    private String name;

    /**
     * User's surname.
     */
    private String surname;

    /**
     * User's birthday.
     */
    private LocalDate birthDate;

    /**
     * User's email.
     */
    private String email;

}
