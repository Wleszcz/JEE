package company.device.model.function;

import company.device.entity.Device;
import company.device.model.DeviceModel;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Converts {@link Device} to {@link DeviceModel}.
 */
public class DeviceToModelFunction implements Function<Device, DeviceModel>, Serializable {

    @Override
    public DeviceModel apply(Device entity) {
        return DeviceModel.builder()
                .name(entity.getName())
                .mass(entity.getMass())
                .price(entity.getPrice())
                .deviceType(entity.getDeviceType())
                .user(entity.getUser().getName())
                .brand(entity.getBrand().getName())
                .version(entity.getVersion())
                .build();
    }

}
