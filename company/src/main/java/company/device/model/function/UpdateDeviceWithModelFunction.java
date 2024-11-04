package company.device.model.function;

import company.user.entity.User;
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
    public Device apply(Device entity, DeviceEditModel model) {
        return Device.builder()
                .id(entity.getId())
                .name(model.getName())
                .mass(model.getMass())
                .price(model.getPrice())
                .deviceType(entity.getDeviceType())
                .brand(entity.getBrand())
                .user(User.builder()
                        .id(model.getUser().getId())
                        .build())
                .build();
    }

}
