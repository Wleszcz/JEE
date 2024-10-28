package company.device.model.function;

import lombok.SneakyThrows;
import company.device.entity.Device;
import company.device.model.DeviceEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

/**
 * Returns new instance of {@link Device} based on provided value and updated with values from
 * {@link DeviceEditModel}.
 */
public class UpdateDeviceWithModelFunction implements BiFunction<Device, DeviceEditModel, Device>, Serializable {

    @Override
    @SneakyThrows
    public Device apply(Device entity, DeviceEditModel request) {
        return Device.builder()
                .id(entity.getId())
                .name(request.getName())
                .mass(request.getMass())
                .price(request.getPrice())
                .deviceType(entity.getDeviceType())
                .brand(entity.getBrand())
                .build();
    }

}
