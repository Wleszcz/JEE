package company.device.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * GET Brand response. Described details about selected Brand. Can be used to present description while
 * device creation or on device's stat page. How Brand is described is defined in
 * {@link company.device.entity.Brand}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetBrandResponse {
    /**
     * Unique id identifying brand.
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
