package company.device.model.function;

import company.device.entity.Device;
import company.device.model.DeviceEditModel;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Converts {@link Device} to {@link DeviceEditModel}.
 */
public class DeviceToEditModelFunction implements Function<Device, DeviceEditModel>, Serializable {

    /**
     */
    private final UserToModelFunction userToModelFunction;

    /**
     */
    public DeviceToEditModelFunction(UserToModelFunction userToModelFunction) {
        this.userToModelFunction = userToModelFunction;
    }


    @Override
    public DeviceEditModel apply(Device entity) {
        return DeviceEditModel.builder()
                .name(entity.getName())
                .mass(entity.getMass())
                .price(entity.getPrice())
                .deviceType(entity.getDeviceType())
                .user(userToModelFunction.apply(entity.getUser()))
                .build();
    }

}
