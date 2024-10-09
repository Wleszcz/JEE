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
 * PUT device request. Contains only fields that can be set up byt the user while creating a new device.How
 * device is described is defined in {@link GetDevicesResponse.Device} and
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutDeviceRequest {

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
     * Identifier of the device's Brand.
     */
    private UUID brand;

    /**
     * Device's type.
     */
    private DeviceType deviceType;


}
