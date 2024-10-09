package company.device.dto.function;

import company.device.dto.PatchDeviceRequest;
import company.device.entity.Device;

import java.util.function.BiFunction;

/**
 * Returns new instance of {@link Device} based on provided value and updated with values from
 * {@link PatchDeviceRequest}.
 */
public class UpdateDeviceWithRequestFunction implements BiFunction<Device, PatchDeviceRequest, Device> {

    @Override
    public Device apply(Device entity, PatchDeviceRequest request) {
        return Device.builder()
                .id(entity.getId())
                .name(request.getName())
                .price(request.getPrice())
                .deviceType(request.getDeviceType())
                .mass(entity.getMass())
                .brand(entity.getBrand())
                .image(entity.getImage())
                .build();
    }

}
