package company.user.model;

import lombok.*;

import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents single user to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UserModel {


    /**
     * User's id.
     */
    private UUID id;

    /**
     * Name of the character.
     */
    private String login;

    private String name;
}
