package company.device.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.DateTimeConverter;
import jakarta.faces.convert.FacesConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FacesConverter(value = "localDateTimeConverter")
public class LocalDateTimeConverter extends DateTimeConverter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Custom conversion logic, if needed
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof LocalDate) {
            return ((LocalDate) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("HH:mm:ss | dd-MM-yyyy"));
        }
        return super.getAsString(context, component, value);
    }
}
