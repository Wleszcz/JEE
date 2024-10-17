package company.device.dto.function;

import company.device.dto.PutDeviceRequest;
import company.device.entity.Device;
import company.device.entity.Brand;
import jakarta.enterprise.context.Dependent;

import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Converts {@link PutDeviceRequest} to {@link Device}. Caution, some fields are not set as they should be updated
 * by business logic.
 */
@Dependent
public class RequestToDeviceFunction implements BiFunction<UUID, PutDeviceRequest, Device> {

    @Override
    public Device apply(UUID id, PutDeviceRequest request) {
        return Device.builder()
                .id(id)
                .name(request.getName())
                .price(request.getPrice())
                .deviceType(request.getDeviceType())
                .mass(request.getMass())
                .brand(Brand.builder()
                        .id(request.getBrand())
                        .build())
                .build();
    }

}
