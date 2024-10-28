package company.device.model.converter;

import company.component.ModelFunctionFactory;
import company.device.entity.Brand;
import company.device.service.BrandService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import java.util.UUID;

@FacesConverter("brandConverter")
public class BrandConverter implements Converter<Brand> {

    BrandService brandService;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Brand brand) {
        if (brand == null) {
            return "";
        }
        return String.valueOf(brand.getId());
    }

    @Override
    public Brand getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        UUID id = UUID.fromString(value);
        return findBrandById(id); // Metoda, która wyszukuje Brand po ID.
    }

    private Brand findBrandById(UUID id) {
        return this.brandService.find(id).orElseThrow(() -> new IllegalArgumentException("Brand not found"));
    }

    @Inject
    public BrandConverter(BrandService service) {
        this.brandService = service;
    }
}

