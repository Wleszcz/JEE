package company.device.dto.function;

import company.device.dto.PutBrandRequest;
import company.device.dto.PutDeviceRequest;
import company.device.entity.Brand;
import company.device.entity.Device;
import jakarta.enterprise.context.Dependent;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Converts {@link PutDeviceRequest} to {@link Device}. Caution, some fields are not set as they should be updated
 * by business logic.
 */
@Dependent
public class RequestToBrandFunction implements BiFunction<UUID, PutBrandRequest, Brand> {

    @Override
    public Brand apply(UUID id, PutBrandRequest request) {
        return Brand.builder()
                .id(id)
                .name(request.getName())
                .dateOfEstablishment(Date.from(request.getDateOfEstablishment().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
    }
}
