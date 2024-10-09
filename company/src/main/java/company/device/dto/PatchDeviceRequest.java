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

/**
 * PATCH device request. Contains all fields that can be updated by the user. How device is described is defined
 * in {@link GetDevicesResponse.Device} and
 * {@link company.creature.entity.Creature} classes.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchDeviceRequest {

    /**
     * Device's name.
     */
    private String name;

    /**
     * Device's mass.
     */
    private Integer mass;

    /**
     * Devicesr's name.
     */
    private Integer price;

    /**
     * Devices's type.
     */
    private DeviceType deviceType;

}
