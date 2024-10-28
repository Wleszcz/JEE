package company.device.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents list of devices to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BrandsModel implements Serializable {

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
    public static class Brand {

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
    private List<Brand> brands;

}
