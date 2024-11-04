package company.user.dto;

import lombok.*;

/**
 * PUT password request. Usually there are separate forms for updating user profile (name, etc.) and password.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutPasswordRequest {

    /**
     * New value for user's password.
     */
    private String password;

}
