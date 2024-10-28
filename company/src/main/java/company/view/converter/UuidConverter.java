package company.view.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import java.util.UUID;

/**
 * Faces converter for {@link UUID}. Automatically applied to inputs and parameters.
 */
@FacesConverter(forClass = UUID.class)
public class UuidConverter implements Converter<UUID> {

    @Override
    public UUID getAsObject(FacesContext context, UIComponent component, String value) {
        return UUID.fromString(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, UUID value) {
        return value.toString();
    }

}
