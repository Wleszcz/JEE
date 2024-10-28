package company.device.model;

import company.device.entity.DeviceType;
import company.user.entity.User;
import lombok.*;

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
public class DeviceCreateModel {

    /**
     * Device's id.
     */
    private UUID id;

    /**
     * Name of the device.
     */
    private String name;

    /**
     * Device's age.
     */
    private int mass;

    /**
     * Device's strength.
     */
    private int price;


    /**
     * Devices type .
     */
    private DeviceType deviceType;

    /**
     * Device's total experience.
     */
    private User user;

    /**
     * Name of the device's brand.
     */
    private BrandModel brand;
}
