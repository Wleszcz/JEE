package company.device.dto.function;

import company.device.dto.PatchBrandRequest;
import company.device.dto.PatchDeviceRequest;
import company.device.entity.Brand;
import company.device.entity.Device;
import jakarta.enterprise.context.Dependent;

import java.time.ZoneId;
import java.util.Date;
import java.util.function.BiFunction;

/**
 * Returns new instance of {@link Brand} based on provided value and updated with values from
 * {@link PatchBrandRequest}.
 */
@Dependent
public class UpdateBrandWithRequestFunction implements BiFunction<Brand, PatchBrandRequest, Brand> {

    @Override
    public Brand apply(Brand entity, PatchBrandRequest request) {
        return Brand.builder()
                .id(entity.getId())
                .name(request.getName())
                .dateOfEstablishment(Date.from(request.getDateOfEstablishment().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
    }

}
