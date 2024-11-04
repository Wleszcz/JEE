package company.device.model;

import company.device.entity.DeviceType;
import company.user.model.UserModel;
import jakarta.servlet.http.Part;
import lombok.*;

/**
 * JSF view model class in order to not use entity classes. Represents single device to be edited. Includes
 * only fields which can be edited after device creation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class DeviceEditModel {

    /**
     * Name of the device.
     */
    private String name;

    /**
     * Device's background story.
     */
    private Integer mass;

    /**
     * Device's age.
     */
    private int price;

    /**
     * Devices type .
     */
    private DeviceType deviceType;


    /**
     * Character's owner.
     */
    private UserModel user;

}
