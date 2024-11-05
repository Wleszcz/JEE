package company.device.model.function;

import company.device.entity.Brand;
import company.device.entity.Device;
import company.device.model.BrandCreateModel;
import company.device.model.DeviceCreateModel;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

/**
 * Converts {@link DeviceCreateModel} to {@link Device}.
 */
public class ModelToBrandFunction implements Function<BrandCreateModel, Brand>, Serializable {

    @Override
    @SneakyThrows
    public Brand apply(BrandCreateModel model) {
        return Brand.builder()
                .id(model.getId())
                .name(model.getName())
                .dateOfEstablishment(Date.from(model.getDateOfEstablishment().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
    }

}
