package company.device.model;

import lombok.*;

import java.util.Date;
import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents single brand to be displayed or selected.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BrandModel {

    /**
     * Brand's id.
     */
    private UUID id;

    /**
     * Name of the brand.
     */
    private String name;

    /**
     * Date of establishment
     */
    private Date dateOfEstablishment;


}
