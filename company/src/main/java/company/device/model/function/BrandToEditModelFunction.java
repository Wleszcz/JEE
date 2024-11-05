package company.device.model.function;

import company.device.entity.Brand;
import company.device.entity.Device;
import company.device.model.BrandEditModel;
import company.device.model.DeviceEditModel;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.function.Function;

/**
 * Converts {@link Device} to {@link DeviceEditModel}.
 */
public class BrandToEditModelFunction implements Function<Brand, BrandEditModel>, Serializable {

    @Override
    public BrandEditModel apply(Brand entity) {
        return BrandEditModel.builder()
                .name(entity.getName())
                .dateOfEstablishment(entity.getDateOfEstablishment().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .build();
    }

}
