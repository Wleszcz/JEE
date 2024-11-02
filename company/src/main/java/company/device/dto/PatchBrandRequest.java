package company.device.dto;

import jakarta.ejb.Local;
import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
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
public class PatchBrandRequest {
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
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dateOfEstablishment;
}
