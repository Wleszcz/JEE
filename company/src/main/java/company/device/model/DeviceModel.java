package company.device.model;

import company.device.entity.DeviceType;
import company.user.entity.User;
import lombok.*;

/**
 * JSF view model class in order to not use entity classes. Represents single device to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class DeviceModel {

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
    private String user;

    /**
     * Name of the device's brand.
     */
    private String brand;

}
