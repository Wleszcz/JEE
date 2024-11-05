package company.device.model.function;

import company.device.entity.Brand;
import company.device.entity.Device;
import company.device.model.BrandEditModel;
import company.device.model.DeviceEditModel;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.BiFunction;

/**
 * Returns new instance of {@link Device} based on provided value and updated with values from
 * {@link DeviceEditModel}.
 */
public class UpdateBrandWithModelFunction implements BiFunction<Brand, BrandEditModel, Brand>, Serializable {

    @Override
    @SneakyThrows
    public Brand apply(Brand entity, BrandEditModel model) {
        return Brand.builder()
                .id(entity.getId())
                .name(model.getName())
                .dateOfEstablishment(Date.from(model.getDateOfEstablishment().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
    }
}