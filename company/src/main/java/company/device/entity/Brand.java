package company.device.entity;

import jakarta.inject.Inject;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Entity class for devices Brands. Describes name of the Brand and basic information
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "brands")
public class Brand implements Serializable {

    /**
     * Unique id (primary key).
     */
    @Id
    private UUID id;

    /**
     * Name of the brand.
     */
    private String name;

    /**
     * Date of establishment
     */
    private Date dateOfEstablishment;

    /**
     * List of brand's devices.
     */
    @ToString.Exclude//It's common to exclude lists from toString
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "brand", cascade = CascadeType.REMOVE)
    private List<Device> devices;

}
