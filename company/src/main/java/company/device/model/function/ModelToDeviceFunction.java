package company.device.model.function;

import lombok.SneakyThrows;
import company.device.entity.Device;
import company.device.entity.Brand;
import company.device.model.DeviceCreateModel;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Converts {@link DeviceCreateModel} to {@link Device}.
 */
public class ModelToDeviceFunction implements Function<DeviceCreateModel, Device>, Serializable {

    @Override
    @SneakyThrows
    public Device apply(DeviceCreateModel model) {
        return Device.builder()
                .id(model.getId())
                .name(model.getName())
                .mass(model.getMass())
                .price(model.getPrice())
                .deviceType(model.getDeviceType())
                .brand(Brand.builder()
                        .id(model.getBrand().getId())
                        .build())
                .build();
    }

}
