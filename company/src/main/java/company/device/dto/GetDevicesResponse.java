package company.device.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

/**
 * GET devices response. Contains list of available devices. Can be used to list particular user's devices as
 * well as all devices in brand.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetDevicesResponse {

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
    public static class Device {

        /**
         * Unique id identifying device.
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
    private List<Device> devices;

}
