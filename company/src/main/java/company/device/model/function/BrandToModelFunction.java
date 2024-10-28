package company.device.model.function;

import company.device.entity.Brand;
import company.device.model.BrandModel;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Converts {@link Brand} to {@link BrandModel}.
 */
public class BrandToModelFunction implements Function<Brand, BrandModel>, Serializable {


    @Override
    public BrandModel apply(Brand entity) {
        return BrandModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .dateOfEstablishment(entity.getDateOfEstablishment())
                .build();
    }

}
