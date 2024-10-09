package company.device.dto.function;

import company.device.dto.GetDevicesResponse;
import company.device.entity.Device;

import java.util.List;
import java.util.function.Function;

/**
 * Coverts {@link List<Device>} to {@link GetDevicesResponse}.
 */
public class DevicesToResponseFunction implements Function<List<Device>, GetDevicesResponse> {

    @Override
    public GetDevicesResponse apply(List<Device> entities) {
        return GetDevicesResponse.builder()
                .devices(entities.stream()
                        .map(Device -> GetDevicesResponse.Device.builder()
                                .id(Device.getId())
                                .name(Device.getName())
                                .build())
                        .toList())
                .build();
    }

}
