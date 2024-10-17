package company.device.dto.function;

import company.device.dto.GetBrandResponse;
import company.device.entity.Brand;
import jakarta.enterprise.context.Dependent;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Converts {@link Brand} to {@link GetBrandResponse}.
 */
@Dependent
public class BrandToResponseFunction implements Function<Brand, GetBrandResponse> {

    @Override
    public GetBrandResponse apply(Brand entity) {
        return GetBrandResponse.builder().id(entity.getId()).name(entity.getName()).dateOfEstablishment(entity.getDateOfEstablishment()).build();
    }

}
