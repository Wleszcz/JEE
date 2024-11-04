package company.user.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * GET user response. Contains only fields which can be displayed on frontend. User is defined in
 * {@link company.user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUserResponse {

    /**
     * Unique id identifying character.
     */
    private UUID id;

    /**
     * User's username (login)
     */
    private String login;

    /**
     * User's name.
     */
    private String name;

    /**
     * User's surname.
     */
    private String surname;

    /**
     * User's birthdate.
     */
    private LocalDate birthDate;

    /**
     * User's email.
     */
    private String email;

}
