package company.device.dto.function;

import company.device.dto.GetDeviceResponse;
import company.device.entity.Device;
import jakarta.enterprise.context.Dependent;

import java.util.function.Function;

/**
 * Converts {@link Device} to {@link GetDeviceResponse}.
 */
@Dependent
public class DeviceToResponseFunction implements Function<Device, GetDeviceResponse> {

    @Override
    public GetDeviceResponse apply(Device entity) {
        return GetDeviceResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .deviceType(entity.getDeviceType())
                .mass(entity.getMass())
                .brand(GetDeviceResponse.Brand.builder()
                        .id(entity.getBrand().getId())
                        .name(entity.getBrand().getName())
                        .build())
                .build();
    }

}
