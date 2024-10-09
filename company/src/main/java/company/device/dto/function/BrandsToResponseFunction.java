package company.device.dto.function;

import company.device.dto.GetBrandsResponse;
import company.device.entity.Brand;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<Brand>} to {@link GetBrandsResponse}.
 */
public class BrandsToResponseFunction implements Function<List<Brand>, GetBrandsResponse> {

    @Override
    public GetBrandsResponse apply(List<Brand> entities) {
        return GetBrandsResponse.builder()
                .brands(entities.stream()
                        .map(brand -> GetBrandsResponse.Brand.builder()
                                .id(brand.getId())
                                .name(brand.getName())
                                .build())
                        .toList())
                .build();
    }

}
