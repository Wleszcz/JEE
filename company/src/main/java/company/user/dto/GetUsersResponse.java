package company.user.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * GET users response. Contains names and ids of users in the system. User's name is the same as login.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUsersResponse {

    /**
     * Represents single user in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {

        /**
         * Unique id identifying user.
         */
        private UUID id;

        /**
         * Login of the user.
         */
        private String login;

    }

    /**
     * List of all users.
     */
    @Singular
    private List<User> users;

}
