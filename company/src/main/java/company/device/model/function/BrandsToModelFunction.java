package company.device.model.function;

import company.device.entity.Brand;
import company.device.entity.Device;
import company.device.model.BrandsModel;
import company.device.model.DevicesModel;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<Brand>} to {@link BrandsModel}.
 */
public class BrandsToModelFunction implements Function<List<Brand>, BrandsModel> {

    @Override
    public BrandsModel apply(List<Brand> entity) {
        return BrandsModel.builder()
                .brands(entity.stream()
                        .map(brand -> BrandsModel.Brand.builder()
                                .id(brand.getId())
                                .name(brand.getName())
                                .build())
                        .toList())
                .build();
    }

}
