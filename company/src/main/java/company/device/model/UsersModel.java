package company.device.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents single brand to be displayed or selected.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UsersModel {

    /**
     * Represents single device in list.
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
         * Unique id identifying brand.
         */
        private UUID id;

        /**
         * Name of the device.
         */
        private String name;

    }

    /**
     * Name of the selected devices.
     */
    @Singular
    private List<User> users;

}
