package company.device.model;

import company.device.entity.DeviceType;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
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
public class BrandCreateModel {

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
    private LocalDate dateOfEstablishment;
}
