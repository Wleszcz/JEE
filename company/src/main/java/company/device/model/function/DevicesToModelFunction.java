package company.device.model.function;

import company.device.entity.Device;
import company.device.model.DevicesModel;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<Device>} to {@link DevicesModel}.
 */
public class DevicesToModelFunction implements Function<List<Device>, DevicesModel> {

    @Override
    public DevicesModel apply(List<Device> entity) {
        return DevicesModel.builder()
                .devices(entity.stream()
                        .map(device -> DevicesModel.Device.builder()
                                .id(device.getId())
                                .name(device.getName())
                                .build())
                        .toList())
                .build();
    }

}
