package company.device.dto;

import company.device.entity.DeviceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * GET device response. It contains all field that can be presented (but not necessarily changed) to the used. How
 * device is described is defined in {@link GetDevicesResponse.Device}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetDeviceResponse {

    /**
     * Represents single brand.
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
         * Name of the brand.
         */
        private String name;

    }

    /**
     * Unique id identifying device.
     */
    private UUID id;

    /**
     * Name of the device.
     */
    private String name;

    /**
     * Device's price.
     */
    private Integer price;

    /**
     * Device's mass.
     */
    private Integer mass;

    /**
     * Device's brand.
     */
    private Brand brand;

    /**
     * Device's type.
     */
    private DeviceType deviceType;

}
